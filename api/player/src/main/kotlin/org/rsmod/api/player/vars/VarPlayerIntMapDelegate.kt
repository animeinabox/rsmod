package org.rsmod.api.player.vars

import org.rsmod.api.player.output.VarpSync
import org.rsmod.game.client.Client
import org.rsmod.game.entity.Player
import org.rsmod.game.type.varbit.VarBitType
import org.rsmod.game.type.varp.VarpType
import org.rsmod.game.vars.VarPlayerIntMap
import org.rsmod.utils.bits.withBits

public class VarPlayerIntMapDelegate(
    private val client: Client<Any, Any>,
    private val vars: VarPlayerIntMap,
) {
    public operator fun get(varp: VarpType): Int = vars[varp]

    public operator fun set(varp: VarpType, value: Int) {
        vars.backing[varp.id] = value
        if (varp.transmit) {
            VarpSync.writeVarp(client, varp, value)
        }
    }

    public operator fun get(varp: VarBitType): Int = vars[varp]

    public operator fun set(varp: VarBitType, value: Int) {
        val mappedValue = vars[varp.baseVar]
        val packedValue = mappedValue.withBits(varp.bits, value)
        set(varp.baseVar, packedValue)
    }

    internal companion object {
        fun from(player: Player): VarPlayerIntMapDelegate {
            return VarPlayerIntMapDelegate(client = player.client, vars = player.vars)
        }
    }
}
