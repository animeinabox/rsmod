@file:Suppress("SpellCheckingInspection", "unused")

package org.rsmod.api.config.refs

import org.rsmod.api.config.aliases.ParamBool
import org.rsmod.api.config.aliases.ParamInt
import org.rsmod.api.config.aliases.ParamLoc
import org.rsmod.api.config.aliases.ParamObj
import org.rsmod.api.config.aliases.ParamSeq
import org.rsmod.api.config.aliases.ParamStat
import org.rsmod.api.config.aliases.ParamStr
import org.rsmod.api.config.aliases.ParamSynth
import org.rsmod.api.type.refs.param.ParamReferences

typealias params = BaseParams

object BaseParams : ParamReferences() {
    val wear_op_index: ParamInt = find("wear_op_index", 88714906312)
    val wear_op1: ParamStr = find("wear_op1", 97133155617)
    val wear_op2: ParamStr = find("wear_op2", 97133155618)
    val wear_op3: ParamStr = find("wear_op3", 97133155619)
    val wear_op4: ParamStr = find("wear_op4", 97133155620)
    val wear_op5: ParamStr = find("wear_op5", 97133155621)
    val wear_op6: ParamStr = find("wear_op6", 97133155622)
    val wear_op7: ParamStr = find("wear_op7", 97133155623)
    val wear_op8: ParamStr = find("wear_op8", 97133155624)
    val statreq1_skill: ParamStat = find("statreq1_skill")
    val statreq1_level: ParamInt = find("statreq1_level", 88687192592)
    val statreq2_skill: ParamStat = find("statreq2_skill")
    val statreq2_level: ParamInt = find("statreq2_level", 88687192593)
    val no_bank: ParamInt = find("no_bank")
    val no_alchemy: ParamInt = find("no_alchemy")

    /* Server-side only types */
    val attackrange: ParamInt = find("attack_range")
    val attack_anim_accurate: ParamSeq = find("attack_anim_accurate")
    val attack_sound_accurate: ParamSynth = find("attack_sound_accurate")
    val attack_anim_aggressive: ParamSeq = find("attack_anim_aggressive")
    val attack_sound_aggressive: ParamSynth = find("attack_sound_aggressive")
    val attack_anim_controlled: ParamSeq = find("attack_anim_controlled")
    val attack_sound_controlled: ParamSynth = find("attack_sound_controlled")
    val attack_anim_defensive: ParamSeq = find("attack_anim_defensive")
    val attack_sound_defensive: ParamSynth = find("attack_sound_defensive")
    val bas_readyanim: ParamSeq = find("bas_readyanim")
    val bas_turnonspot: ParamSeq = find("bas_turnonspot")
    val bas_walk_f: ParamSeq = find("bas_walk_f")
    val bas_walk_b: ParamSeq = find("bas_walk_b")
    val bas_walk_l: ParamSeq = find("bas_walk_l")
    val bas_walk_r: ParamSeq = find("bas_walk_r")
    val bas_running: ParamSeq = find("bas_running")
    val attack_anim: ParamSeq = find("attack_anim")
    val defend_anim: ParamSeq = find("defend_anim")
    val attackrate: ParamInt = find("attackrate")
    val equipment_sound: ParamSynth = find("equipment_sound")
    val player_op5_text: ParamStr = find("player_op5_text")
    val release_note_title: ParamStr = find("release_note_title")
    val release_note_message: ParamStr = find("release_note_message")
    val statreq_failmessage1: ParamStr = find("statreq_failmessage1")
    val statreq_failmessage2: ParamStr = find("statreq_failmessage2")
    val destroy_note_title: ParamStr = find("destroy_note_title")
    val destroy_note_desc: ParamStr = find("destroy_note_desc")
    val xpmod_percent: ParamInt = find("xpmod_percent")
    val xpmod_stat: ParamStat = find("xpmod_stat")
    val respawn_time_low: ParamInt = find("respawn_time_low")
    val respawn_time_high: ParamInt = find("respawn_time_high")
    val respawn_time: ParamInt = find("respawn_time")
    val despawn_time: ParamInt = find("despawn_time")
    val deplete_chance: ParamInt = find("deplete_chance")
    val skill_sound: ParamSynth = find("skill_sound")
    val skill_productitem: ParamObj = find("skill_productitem")
    val skill_xp: ParamInt = find("skill_xp")
    val skill_anim: ParamSeq = find("skill_anim")
    val skill_levelreq: ParamInt = find("skill_levelreq")
    val game_message: ParamStr = find("game_message")
    val game_message2: ParamStr = find("game_message2")
    val climb_anim: ParamSeq = find("climb_anim")
    val closesound: ParamSynth = find("closesound")
    val opensound: ParamSynth = find("opensound")
    val next_loc_stage: ParamLoc = find("next_loc_stage")
    val shop_sale_restricted: ParamBool = find("shop_sale_restricted")
}
