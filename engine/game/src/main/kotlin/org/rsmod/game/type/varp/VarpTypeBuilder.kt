package org.rsmod.game.type.varp

import org.rsmod.game.type.util.GenericPropertySelector.select

@DslMarker private annotation class VarpBuilderDsl

@VarpBuilderDsl
public class VarpTypeBuilder(public var internal: String? = null) {
    public var clientCode: Int? = null
    public var transmit: Boolean? = null
    public var protect: Boolean? = null
    /** If set to `true` this varp will check all associated varbits to detect bit collisions. */
    public var bitProtect: Boolean? = null

    public fun build(id: Int): UnpackedVarpType {
        val internal = checkNotNull(internal) { "`internal` must be set." }
        val clientCode = clientCode ?: DEFAULT_CLIENT_CODE
        val transmit = transmit ?: DEFAULT_TRANSMIT
        val protect = protect ?: DEFAULT_PROTECT
        val bitProtect = bitProtect ?: DEFAULT_BIT_PROTECT
        return UnpackedVarpType(
            bitProtect = bitProtect,
            clientCode = clientCode,
            transmit = transmit,
            protect = protect,
            internalId = id,
            internalName = internal,
        )
    }

    public companion object {
        public const val DEFAULT_CLIENT_CODE: Int = -1
        public const val DEFAULT_PROTECT: Boolean = false // Don't really want this to default true.
        public const val DEFAULT_TRANSMIT: Boolean = true
        public const val DEFAULT_BIT_PROTECT: Boolean = false

        public fun merge(edit: UnpackedVarpType, base: UnpackedVarpType): UnpackedVarpType {
            val clientCode = select(edit, base, DEFAULT_CLIENT_CODE) { clientCode }
            val transmit = select(edit, base, DEFAULT_TRANSMIT) { transmit }
            val protect = select(edit, base, DEFAULT_PROTECT) { protect }
            val bitProtect = select(edit, base, DEFAULT_BIT_PROTECT) { bitProtect }
            val internalId = select(edit, base, default = null) { internalId }
            val internalName = select(edit, base, default = null) { internalName }
            return UnpackedVarpType(
                bitProtect = bitProtect,
                clientCode = clientCode,
                transmit = transmit,
                protect = protect,
                internalId = internalId ?: -1,
                internalName = internalName ?: "",
            )
        }
    }
}
