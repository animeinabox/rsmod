package org.rsmod.objtx

public class TransactionInventory<T>(
    public val stackType: StackType,
    public val output: Array<T?>,
    public val image: Array<TransactionObj?>,
    public val placeholders: Boolean = false,
) {
    internal val stackAll: Boolean
        get() = stackType == AlwaysStack

    internal val stackNone: Boolean
        get() = stackType == NeverStack

    internal fun indexOfNull(startIndex: Int = 0): Int? {
        for (i in image.indices) {
            val slot = (i + startIndex) % image.size
            if (image[slot] == null) {
                return slot
            }
        }
        return null
    }

    internal fun indexesOf(
        obj: Int,
        max: Int,
        startIndex: Int = 0,
        slots: MutableList<Int> = emptyIndexList(),
    ): List<Int> {
        for (i in image.indices) {
            val slot = (i + startIndex) % image.size
            if (image[slot]?.id == obj) {
                slots += slot
                if (slots.size >= max) {
                    break
                }
            }
        }
        return slots
    }

    internal fun indexOf(obj: Int, startSlot: Int = 0): Int? {
        for (i in image.indices) {
            val slot = (i + startSlot) % image.size
            if (image[slot]?.id == obj) {
                return slot
            }
        }
        return null
    }

    internal operator fun set(slot: Int, obj: TransactionObj?) {
        image[slot] = obj
    }

    internal operator fun get(slot: Int): TransactionObj? = image[slot]

    public sealed class StackType

    public data object NormalStack : StackType()

    public data object AlwaysStack : StackType()

    public data object NeverStack : StackType()

    private companion object {
        private val reusableIndexList = ArrayList<Int>()

        private fun emptyIndexList(): MutableList<Int> {
            reusableIndexList.clear()
            return reusableIndexList
        }
    }
}