package org.rsmod.plugins.api.session

import com.github.michaelbull.logging.InlineLogger
import org.openrs2.crypto.SymmetricKey
import org.rsmod.game.client.Client
import org.rsmod.game.map.Coordinates
import org.rsmod.game.map.zone.ZoneKey
import org.rsmod.game.model.mob.list.PlayerList
import org.rsmod.plugins.api.cache.map.xtea.XteaRepository
import org.rsmod.plugins.api.net.downstream.GPIInitialization
import org.rsmod.plugins.api.net.downstream.RebuildNormal
import org.rsmod.plugins.api.refreshBuildArea
import org.rsmod.plugins.api.util.BuildAreaUtils
import org.rsmod.plugins.info.model.coord.LowResCoord
import javax.inject.Inject
import javax.inject.Singleton

private val logger = InlineLogger()

@Singleton
public class ClientGameSession @Inject constructor(
    private val players: PlayerList,
    private val xteaRepository: XteaRepository
) {

    public fun connect(client: Client) {
        val player = client.player
        val channel = client.channel
        val rebuildNormal = createRebuildNormal(player.index, player.coords)
        // TODO: move to game cycle (check if build area should be refreshed)
        player.refreshBuildArea(player.coords)
        // REBUILD_NORMAL should be priority and the first packet to be
        // sent downstream.
        player.downstream.add(0, rebuildNormal)
        player.downstream.flush(channel).clear()
        logger.debug { "Client connected: $client." }
    }

    public fun disconnect(client: Client) {
        logger.debug { "Client disconnected: $client." }
    }

    private fun createRebuildNormal(playerIndex: Int, playerCoords: Coordinates): RebuildNormal {
        val zone = ZoneKey.from(playerCoords)
        val xtea = mutableListOf<Int>()
        zone.toViewport(BuildAreaUtils.ZONE_VIEW_RADIUS).forEach {
            val key = xteaRepository[it] ?: SymmetricKey.ZERO
            xtea += key.k0
            xtea += key.k1
            xtea += key.k2
            xtea += key.k3
        }
        val gpi = GPIInitialization(playerCoords.packed, players.playerCoords(excludeIndex = playerIndex))
        return RebuildNormal(gpi, zone, xtea)
    }

    private fun PlayerList.playerCoords(excludeIndex: Int): List<Int> = mapIndexedNotNull { index, player ->
        if (index != excludeIndex) {
            player?.coords?.let { LowResCoord(it.x, it.z, it.level).packed } ?: 0
        } else {
            null
        }
    }
}
