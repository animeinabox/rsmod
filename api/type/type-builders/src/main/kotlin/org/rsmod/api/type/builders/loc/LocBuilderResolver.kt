package org.rsmod.api.type.builders.loc

import jakarta.inject.Inject
import kotlin.collections.get
import org.rsmod.api.type.builders.TypeBuilder
import org.rsmod.api.type.builders.resolver.TypeBuilderResolver
import org.rsmod.api.type.builders.resolver.TypeBuilderResult
import org.rsmod.api.type.builders.resolver.TypeBuilderResult.CachePackRequired
import org.rsmod.api.type.builders.resolver.TypeBuilderResult.FullSuccess
import org.rsmod.api.type.builders.resolver.TypeBuilderResult.NameNotFound
import org.rsmod.api.type.builders.resolver.err
import org.rsmod.api.type.builders.resolver.ok
import org.rsmod.api.type.builders.resolver.update
import org.rsmod.api.type.symbols.name.NameMapping
import org.rsmod.game.type.TypeResolver
import org.rsmod.game.type.loc.LocTypeBuilder
import org.rsmod.game.type.loc.LocTypeList
import org.rsmod.game.type.loc.UnpackedLocType

public class LocBuilderResolver
@Inject
constructor(private val types: LocTypeList, private val nameMapping: NameMapping) :
    TypeBuilderResolver<LocTypeBuilder, UnpackedLocType> {
    private val names: Map<String, Int>
        get() = nameMapping.locs

    override fun resolve(
        builders: TypeBuilder<LocTypeBuilder, UnpackedLocType>
    ): List<TypeBuilderResult> = builders.cache.map { it.resolve() }

    private fun UnpackedLocType.resolve(): TypeBuilderResult {
        val internalId = names[internalNameGet] ?: return err(NameNotFound(internalNameGet))
        val cacheType = types[internalId]

        TypeResolver[this] = internalId

        return if (cacheType != this) {
            update(CachePackRequired)
        } else {
            ok(FullSuccess)
        }
    }
}