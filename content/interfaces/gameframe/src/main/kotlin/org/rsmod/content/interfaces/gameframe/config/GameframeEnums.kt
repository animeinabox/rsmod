@file:Suppress("unused")

package org.rsmod.content.interfaces.gameframe.config

import org.rsmod.api.config.aliases.EnumComp
import org.rsmod.api.type.refs.enums.EnumReferences

object GameframeEnums : EnumReferences() {
    val fixed_pane_redirect = find<EnumComp, EnumComp>("fixed_pane_redirect", 4205535)
    val resizable_basic_pane_redirect =
        find<EnumComp, EnumComp>("resizable_basic_pane_redirect", 4209256)
    val side_panels_resizable_pane_redirect =
        find<EnumComp, EnumComp>("side_panels_resizable_pane_redirect", 4212977)
}

// [IF_RESYNC] id=161
//        Sub Interfaces
//            IfOpenSub(com=161:35, id=steam_side_panel, type=Overlay)
//            IfOpenSub(com=161:77, id=skills_tab, type=Overlay)
//            IfOpenSub(com=161:88, id=emote_tab, type=Overlay)
//            IfOpenSub(com=161:33, id=orbs, type=Overlay)
//            IfOpenSub(com=161:76, id=combat_tab, type=Overlay)
//            IfOpenSub(com=161:96, id=chat, type=Overlay)
//            IfOpenSub(com=161:3, id=wilderness_overlay, type=Overlay)
//            IfOpenSub(com=161:9, id=experience_drops_window, type=Overlay)
//            IfOpenSub(com=161:82, id=spellbook_tab, type=Overlay)
//            IfOpenSub(com=161:6, id=651, type=Overlay)
//            IfOpenSub(com=161:93, id=private_chat, type=Overlay)
//            IfOpenSub(com=161:80, id=equipment_tab, type=Overlay)
//            IfOpenSub(com=161:83, id=chat_header, type=Overlay)
//            IfOpenSub(com=161:5, id=708, type=Overlay)
//            IfOpenSub(com=161:87, id=settings_tab, type=Overlay)
//            IfOpenSub(com=161:78, id=journal_header_tab, type=Overlay)
//            IfOpenSub(com=161:79, id=inventory_tab, type=Overlay)
//            IfOpenSub(com=161:81, id=prayer_tab, type=Overlay)
//            IfOpenSub(com=161:85, id=friend_list_tab, type=Overlay)
//            IfOpenSub(com=161:84, id=account_management_tab, type=Overlay)
//            IfOpenSub(com=161:86, id=world_switcher, type=Overlay)
//            IfOpenSub(com=161:2, id=hp_hud, type=Overlay)
//            IfOpenSub(com=161:89, id=music_tab type=Overlay)
//        Events
//            IfSetEvents(com=69:18, start=0, end=600, events=[OP1, OP2])
//            IfSetEvents(com=76:30, start=0, end=26, events=[OP1])
//            IfSetEvents(com=116:23, start=0, end=21, events=[OP1])
//            IfSetEvents(com=116:38, start=1, end=5, events=[OP1])
//            IfSetEvents(com=116:39, start=1, end=4, events=[OP1])
//            IfSetEvents(com=116:41, start=1, end=3, events=[OP1])
//            IfSetEvents(com=116:90, start=0, end=21, events=[OP1])
//            IfSetEvents(com=116:104, start=0, end=21, events=[OP1])
//            IfSetEvents(com=116:118, start=0, end=21, events=[OP1])
//            IfSetEvents(com=116:133, start=0, end=21, events=[OP1])
//            IfSetEvents(com=149:0, start=0, end=27, events=[OP2, OP3, OP4, OP6, OP7, OP10, TGTOBJ,
// TGTNPC, TGTLOC, TGTPLAYER, TGTINV, TGTCOM, DEPTH1, DRAGTARGET, TARGET])
//            IfSetEvents(com=161:42, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:43, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:44, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:45, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:46, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:47, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:48, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:58, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:59, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:60, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:61, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:62, start=-1, end=-1, events=[OP1])
//            IfSetEvents(com=161:63, start=-1, end=-1, events=[OP1, OP2])
//            IfSetEvents(com=161:64, start=-1, end=-1, events=[OP1, OP2])
//            IfSetEvents(com=216:2, start=0, end=53, events=[OP1, OP2, OP3])
//            IfSetEvents(com=218:198, start=0, end=6, events=[OP1])
//            IfSetEvents(com=239:6, start=0, end=776, events=[OP1, OP2])
//            IfSetEvents(com=541:42, start=0, end=4, events=[OP1])
//            IfSetEvents(com=712:3, start=3, end=7, events=[OP1, OP2, OP3, OP4])
//            IfSetEvents(com=728:15, start=0, end=1, events=[OP1])
