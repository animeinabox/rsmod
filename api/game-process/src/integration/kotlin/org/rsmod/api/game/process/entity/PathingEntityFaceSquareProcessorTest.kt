package org.rsmod.api.game.process.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.rsmod.api.game.process.npc.NpcMovementProcessor
import org.rsmod.api.game.process.player.PlayerMovementProcessor
import org.rsmod.api.testing.GameTestState
import org.rsmod.api.testing.factory.entityFactory
import org.rsmod.api.testing.factory.npcFactory
import org.rsmod.api.testing.factory.npcTypeFactory
import org.rsmod.api.testing.params.TestArgs
import org.rsmod.api.testing.params.TestArgsProvider
import org.rsmod.api.testing.params.TestWithArgs
import org.rsmod.game.map.Direction
import org.rsmod.map.CoordGrid
import org.rsmod.map.square.MapSquareKey

class PathingEntityFaceSquareProcessorTest {
    @Test
    fun GameTestState.`face angle is delayed until no movement`() = runGameTest {
        withCollisionState {
            val start = CoordGrid(0, 0, 0, 10, 10)
            val moveDest = CoordGrid(0, 0, 0, 10, 15)
            val faceTarget = CoordGrid(0, 0, 0, 15, 15)
            it.allocateCollision(MapSquareKey(0, 0))

            // Test with npc.
            val type = npcTypeFactory.create()
            val npc = npcFactory.create(type, start)
            withNpc(npc) {
                val move = NpcMovementProcessor(it.collision, it.stepFactory)
                val face = PathingEntityFaceSquareProcessor()
                fun process() {
                    previousCoords = coords
                    currentMapClock++
                    move.process(this)
                    face.process(this)
                }

                fun processUntilArrival(dest: CoordGrid, tracker: Int = 0) {
                    check(tracker < 1000)
                    if (coords != dest) {
                        process()
                        processUntilArrival(dest, tracker + 1)
                    }
                }

                walk(moveDest)
                faceSquare(faceTarget, targetWidth = 1, targetLength = 1)
                check(faceAngle == 0)

                process()
                // After processing, the npc should have moved. This means the face angle should not
                // be calculated yet, and pending face square should remain as its initial value.
                check(routeDestination.lastOrNull() == moveDest)
                check(hasMovedThisTick)
                assertEquals(-1, faceAngle)
                assertEquals(faceTarget, pendingFaceSquare)

                processUntilArrival(moveDest)

                // Now that the npc has arrived, we have to wait 1 tick (process) before the face
                // angle is set due to the `hasMovedThisTick` condition.
                process()

                assertEquals(Direction.East.angle, faceAngle)
                assertEquals(CoordGrid.NULL, pendingFaceSquare)
            }

            // Test with player.
            withPlayer(start) {
                val move = PlayerMovementProcessor(it.collision, it.routeFactory, it.stepFactory)
                val face = PathingEntityFaceSquareProcessor()
                fun process() {
                    previousCoords = coords
                    currentMapClock++
                    move.process(this)
                    face.process(this)
                }

                fun processUntilArrival(dest: CoordGrid, tracker: Int = 0) {
                    check(tracker < 1000)
                    if (coords != dest) {
                        process()
                        processUntilArrival(dest, tracker + 1)
                    }
                }

                walk(moveDest)
                faceSquare(faceTarget, targetWidth = 1, targetLength = 1)
                check(faceAngle == 0)

                process()
                // After processing, the player should have moved. This means the face angle should
                // not be calculated yet, and pending face square should remain as its initial
                // value.
                check(routeDestination.lastOrNull() == moveDest)
                check(hasMovedThisTick)
                assertEquals(-1, faceAngle)
                assertEquals(faceTarget, pendingFaceSquare)

                processUntilArrival(moveDest)

                // Now that the player has arrived, we have to wait 1 tick (process) before the face
                // angle is set due to the `hasMovedThisTick` condition.
                process()

                assertEquals(Direction.East.angle, faceAngle)
                assertEquals(CoordGrid.NULL, pendingFaceSquare)
            }
        }
    }

    @Test
    fun GameTestState.`send default face angle on player init`() = runGameTest {
        withPlayer {
            val facing = PathingEntityFaceSquareProcessor()
            fun process() {
                previousCoords = coords
                currentMapClock++
                facing.process(this)
            }
            check(faceAngle == 0)
            check(pendingFaceSquare == CoordGrid.ZERO)

            process()
            assertEquals(0, faceAngle)
        }
    }

    @Test
    fun GameTestState.`reset pending face square after setting face angle`() = runGameTest {
        withPlayer {
            val facing = PathingEntityFaceSquareProcessor()
            fun process() {
                previousCoords = coords
                currentMapClock++
                facing.process(this)
            }
            check(pendingFaceSquare != CoordGrid.NULL)

            faceSquare(CoordGrid(0, 0, 0, 1, 1), targetWidth = 1, targetLength = 1)

            process()
            assertNotEquals(-1, faceAngle)
            assertEquals(CoordGrid.NULL, pendingFaceSquare)
        }
    }

    @TestWithArgs(DirectionProvider::class)
    fun `calculate correct angle based on direction`(dir: Direction, state: GameTestState) =
        with(state) {
            runGameTest {
                val start = CoordGrid(0, 0, 0, 16, 16)
                val target = start.translate(dir.xOff * 5, dir.zOff * 5)
                val entity = entityFactory.create(start)
                withPathingEntity(entity) {
                    val facing = PathingEntityFaceSquareProcessor()
                    check(faceAngle == 0)

                    // Set the _pending_ face square to target.
                    faceSquare(target, targetWidth = 1, targetLength = 1)
                    check(faceAngle == 0)

                    facing.process(this)

                    assertEquals(dir.angle, faceAngle)
                    assertEquals(CoordGrid.NULL, pendingFaceSquare)
                }
            }
        }

    private object DirectionProvider : TestArgsProvider {
        override fun args(): List<TestArgs> = Direction.entries.map { TestArgs(it) }
    }
}