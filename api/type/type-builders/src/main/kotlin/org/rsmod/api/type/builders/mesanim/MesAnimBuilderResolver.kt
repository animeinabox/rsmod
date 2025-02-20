package org.rsmod.api.type.builders.mesanim

import jakarta.inject.Inject
import org.rsmod.api.type.builders.TypeBuilder
import org.rsmod.api.type.builders.resolver.TypeBuilderResolver
import org.rsmod.api.type.builders.resolver.TypeBuilderResult
import org.rsmod.api.type.builders.resolver.TypeBuilderResult.FullSuccess
import org.rsmod.api.type.builders.resolver.TypeBuilderResult.NameNotFound
import org.rsmod.api.type.builders.resolver.err
import org.rsmod.api.type.builders.resolver.ok
import org.rsmod.api.type.script.dsl.MesAnimPluginBuilder
import org.rsmod.api.type.symbols.name.NameMapping
import org.rsmod.game.type.TypeResolver
import org.rsmod.game.type.mesanim.MesAnimType

public class MesAnimBuilderResolver @Inject constructor(private val nameMapping: NameMapping) :
    TypeBuilderResolver<MesAnimPluginBuilder, MesAnimType> {
    private val names: Map<String, Int>
        get() = nameMapping.mesanims

    override fun resolve(
        builders: TypeBuilder<MesAnimPluginBuilder, MesAnimType>
    ): List<TypeBuilderResult> = builders.cache.map { it.resolve() }

    private fun MesAnimType.resolve(): TypeBuilderResult {
        val internalId = names[internalNameGet] ?: return err(NameNotFound(internalNameGet))
        TypeResolver[this] = internalId
        return ok(FullSuccess)
    }
}
