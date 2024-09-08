package org.rsmod.api.parsers.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.inject.Provider
import jakarta.inject.Inject

internal class ObjectMapperProvider @Inject constructor(private val jacksonModules: Set<Module>) :
    Provider<ObjectMapper> {
    override fun get(): ObjectMapper =
        ObjectMapper(JsonFactory())
            .registerKotlinModule()
            .registerModules(jacksonModules)
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
}