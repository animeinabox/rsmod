package org.rsmod.api.net.rsprot.provider

import jakarta.inject.Inject
import jakarta.inject.Singleton
import net.rsprot.protocol.game.incoming.buttons.If3Button
import net.rsprot.protocol.game.incoming.locs.OpLoc
import net.rsprot.protocol.game.incoming.locs.OpLoc6
import net.rsprot.protocol.game.incoming.messaging.MessagePublic
import net.rsprot.protocol.game.incoming.misc.user.ClientCheat
import net.rsprot.protocol.game.incoming.misc.user.CloseModal
import net.rsprot.protocol.game.incoming.misc.user.MoveGameClick
import net.rsprot.protocol.game.incoming.misc.user.MoveMinimapClick
import net.rsprot.protocol.game.incoming.npcs.OpNpc
import net.rsprot.protocol.game.incoming.npcs.OpNpc6
import net.rsprot.protocol.game.incoming.objs.OpObj
import net.rsprot.protocol.game.incoming.objs.OpObj6
import net.rsprot.protocol.game.incoming.resumed.ResumePauseButton
import net.rsprot.protocol.message.codec.incoming.GameMessageConsumerRepositoryBuilder
import net.rsprot.protocol.message.codec.incoming.provider.DefaultGameMessageConsumerRepositoryProvider
import org.rsmod.api.net.rsprot.handlers.ClientCheatHandler
import org.rsmod.api.net.rsprot.handlers.CloseModalHandler
import org.rsmod.api.net.rsprot.handlers.If3ButtonHandler
import org.rsmod.api.net.rsprot.handlers.MessagePublicHandler
import org.rsmod.api.net.rsprot.handlers.MoveGameClickHandler
import org.rsmod.api.net.rsprot.handlers.MoveMinimapClickHandler
import org.rsmod.api.net.rsprot.handlers.OpLoc6Handler
import org.rsmod.api.net.rsprot.handlers.OpLocHandler
import org.rsmod.api.net.rsprot.handlers.OpNpc6Handler
import org.rsmod.api.net.rsprot.handlers.OpNpcHandler
import org.rsmod.api.net.rsprot.handlers.OpObj6Handler
import org.rsmod.api.net.rsprot.handlers.OpObjHandler
import org.rsmod.api.net.rsprot.handlers.ResumePauseButtonHandler
import org.rsmod.game.entity.Player

@Singleton
class MessageConsumerProvider
@Inject
constructor(
    private val moveGameClick: MoveGameClickHandler,
    private val moveMinimapClick: MoveMinimapClickHandler,
    private val opLoc: OpLocHandler,
    private val opLoc6: OpLoc6Handler,
    private val clientCheat: ClientCheatHandler,
    private val opNpc: OpNpcHandler,
    private val opNpc6: OpNpc6Handler,
    private val messagePublic: MessagePublicHandler,
    private val if3Button: If3ButtonHandler,
    private val closeModal: CloseModalHandler,
    private val resumePauseButton: ResumePauseButtonHandler,
    private val opObj: OpObjHandler,
    private val opObj6: OpObj6Handler,
) {
    fun get(): DefaultGameMessageConsumerRepositoryProvider<Player> {
        val builder = GameMessageConsumerRepositoryBuilder<Player>()
        builder.addListener(MoveGameClick::class.java, moveGameClick)
        builder.addListener(MoveMinimapClick::class.java, moveMinimapClick)
        builder.addListener(OpLoc::class.java, opLoc)
        builder.addListener(OpLoc6::class.java, opLoc6)
        builder.addListener(ClientCheat::class.java, clientCheat)
        builder.addListener(OpNpc::class.java, opNpc)
        builder.addListener(OpNpc6::class.java, opNpc6)
        builder.addListener(MessagePublic::class.java, messagePublic)
        builder.addListener(If3Button::class.java, if3Button)
        builder.addListener(CloseModal::class.java, closeModal)
        builder.addListener(ResumePauseButton::class.java, resumePauseButton)
        builder.addListener(OpObj::class.java, opObj)
        builder.addListener(OpObj6::class.java, opObj6)
        return DefaultGameMessageConsumerRepositoryProvider(builder.build())
    }
}