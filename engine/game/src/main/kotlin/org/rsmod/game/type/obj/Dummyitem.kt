package org.rsmod.game.type.obj

public enum class Dummyitem(public val id: Int) {
    /** Cannot be added into inventories or dropped on floor. */
    GraphicOnly(id = 1),
    /** Can be added into inventories, but cannot be dropped on floor. */
    InvOnly(id = 2);

    public companion object {
        public operator fun get(id: Int): Dummyitem? =
            when (id) {
                GraphicOnly.id -> GraphicOnly
                InvOnly.id -> InvOnly
                else -> null
            }
    }
}
