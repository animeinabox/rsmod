package org.rsmod.api.script

import org.rsmod.api.player.events.interact.ObjContentEvents
import org.rsmod.api.player.events.interact.ObjEvents
import org.rsmod.api.player.protect.ProtectedAccess
import org.rsmod.game.type.content.ContentType
import org.rsmod.game.type.obj.ObjType
import org.rsmod.plugin.scripts.ScriptContext

public fun ScriptContext.onOpObj1(
    type: ObjType,
    action: suspend ProtectedAccess.(ObjEvents.Op1) -> Unit,
): Unit = onProtectedEvent<ObjEvents.Op1>(type.id, action)

public fun ScriptContext.onOpObj2(
    type: ObjType,
    action: suspend ProtectedAccess.(ObjEvents.Op2) -> Unit,
): Unit = onProtectedEvent<ObjEvents.Op2>(type.id, action)

public fun ScriptContext.onOpObj3(
    type: ObjType,
    action: suspend ProtectedAccess.(ObjEvents.Op3) -> Unit,
): Unit = onProtectedEvent<ObjEvents.Op3>(type.id, action)

public fun ScriptContext.onOpObj4(
    type: ObjType,
    action: suspend ProtectedAccess.(ObjEvents.Op4) -> Unit,
): Unit = onProtectedEvent<ObjEvents.Op4>(type.id, action)

public fun ScriptContext.onOpObj5(
    type: ObjType,
    action: suspend ProtectedAccess.(ObjEvents.Op5) -> Unit,
): Unit = onProtectedEvent<ObjEvents.Op5>(type.id, action)

public fun ScriptContext.onOpObj1(
    content: ContentType,
    action: suspend ProtectedAccess.(ObjContentEvents.Op1) -> Unit,
): Unit = onProtectedEvent<ObjContentEvents.Op1>(content.id, action)

public fun ScriptContext.onOpObj2(
    content: ContentType,
    action: suspend ProtectedAccess.(ObjContentEvents.Op2) -> Unit,
): Unit = onProtectedEvent<ObjContentEvents.Op2>(content.id, action)

public fun ScriptContext.onOpObj3(
    content: ContentType,
    action: suspend ProtectedAccess.(ObjContentEvents.Op3) -> Unit,
): Unit = onProtectedEvent<ObjContentEvents.Op3>(content.id, action)

public fun ScriptContext.onOpObj4(
    content: ContentType,
    action: suspend ProtectedAccess.(ObjContentEvents.Op4) -> Unit,
): Unit = onProtectedEvent<ObjContentEvents.Op4>(content.id, action)

public fun ScriptContext.onOpObj5(
    content: ContentType,
    action: suspend ProtectedAccess.(ObjContentEvents.Op5) -> Unit,
): Unit = onProtectedEvent<ObjContentEvents.Op5>(content.id, action)