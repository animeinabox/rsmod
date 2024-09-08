package org.rsmod.api.route

import jakarta.inject.Inject
import org.rsmod.game.entity.PathingEntityAvatar
import org.rsmod.game.movement.RouteRequest
import org.rsmod.game.movement.RouteRequestCoord
import org.rsmod.game.movement.RouteRequestLoc
import org.rsmod.game.movement.RouteRequestPathingEntity
import org.rsmod.map.CoordGrid
import org.rsmod.pathfinder.Route
import org.rsmod.pathfinder.RouteFinding
import org.rsmod.pathfinder.collision.CollisionFlagMap
import org.rsmod.pathfinder.collision.CollisionStrategy

public class RouteFactory @Inject constructor(flags: CollisionFlagMap) {
    private val routeFinding = RouteFinding(flags)
    private val threadLocalRouteFinding = ThreadLocal.withInitial { RouteFinding(flags) }
    private var asynchronous: Boolean = false

    private fun routeFinder(async: Boolean): RouteFinding =
        if (async) {
            threadLocalRouteFinding.get()
        } else {
            routeFinding
        }

    public fun create(
        source: CoordGrid,
        destination: CoordGrid,
        collision: CollisionStrategy = CollisionStrategy.Normal,
        async: Boolean = asynchronous,
    ): Route =
        with(routeFinder(async)) {
            findRoute(
                level = source.level,
                srcX = source.x,
                srcZ = source.z,
                destX = destination.x,
                destZ = destination.z,
                srcSize = 1,
                destWidth = 1,
                destLength = 1,
                collision = collision,
            )
        }

    public fun create(
        source: PathingEntityAvatar,
        destination: CoordGrid,
        collision: CollisionStrategy = CollisionStrategy.Normal,
        async: Boolean = asynchronous,
    ): Route =
        with(routeFinder(async)) {
            findRoute(
                level = source.coords.level,
                srcX = source.coords.x,
                srcZ = source.coords.z,
                destX = destination.x,
                destZ = destination.z,
                srcSize = source.size,
                destWidth = 1,
                destLength = 1,
                collision = collision,
            )
        }

    public fun create(
        source: PathingEntityAvatar,
        destination: PathingEntityAvatar,
        collision: CollisionStrategy = CollisionStrategy.Normal,
        async: Boolean = asynchronous,
    ): Route =
        with(routeFinder(async)) {
            findRoute(
                level = source.coords.level,
                srcX = source.coords.x,
                srcZ = source.coords.z,
                destX = destination.coords.x,
                destZ = destination.coords.z,
                srcSize = source.size,
                destWidth = destination.size,
                destLength = destination.size,
                collision = collision,
                locShape = RECTANGLE_EXCLUSIVE_STRATEGY,
            )
        }

    public fun create(
        source: PathingEntityAvatar,
        request: RouteRequestLoc,
        collision: CollisionStrategy = CollisionStrategy.Normal,
    ): Route {
        with(request) {
            val routeFinder = routeFinder(request.async)
            return routeFinder.findRoute(
                level = source.coords.level,
                srcX = source.coords.x,
                srcZ = source.coords.z,
                destX = destination.x,
                destZ = destination.z,
                srcSize = source.size,
                destWidth = width,
                destLength = length,
                locShape = shape,
                locAngle = angle,
                blockAccessFlags = forceApproachFlags,
                collision = collision,
            )
        }
    }

    public fun create(source: PathingEntityAvatar, request: RouteRequest): Route =
        when (request) {
            is RouteRequestCoord -> create(source, request.destination, async = request.async)
            is RouteRequestPathingEntity ->
                create(source, request.destination, async = request.async)
            is RouteRequestLoc -> create(source, request)
        }

    public companion object {
        private const val RECTANGLE_EXCLUSIVE_STRATEGY: Int = -2

        private val RouteRequest.async: Boolean
            get() = !recalc
    }
}