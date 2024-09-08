package org.rsmod.server.app

import com.github.ajalt.clikt.core.CliktCommand
import com.github.michaelbull.logging.InlineLogger
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Key
import com.google.inject.util.Modules
import java.nio.file.Path
import java.text.DecimalFormat
import kotlin.time.measureTime
import org.openrs2.cache.Cache
import org.openrs2.cache.Store
import org.rsmod.annotations.GameCache
import org.rsmod.api.cache.map.GameMapDecoder
import org.rsmod.api.registry.loc.LocRegistry
import org.rsmod.api.type.resolver.TypeCleanup
import org.rsmod.api.type.resolver.TypeResolver
import org.rsmod.api.type.updater.TypeUpdater
import org.rsmod.api.type.verifier.TypeVerifier
import org.rsmod.api.type.verifier.isCacheUpdateRequired
import org.rsmod.api.type.verifier.isFailure
import org.rsmod.plugin.module.PluginModule
import org.rsmod.plugin.scripts.PluginScript
import org.rsmod.plugin.scripts.ScriptContext
import org.rsmod.scheduler.TaskScheduler
import org.rsmod.server.shared.PluginConstants
import org.rsmod.server.shared.loader.PluginModuleLoader
import org.rsmod.server.shared.loader.PluginScriptLoader
import org.rsmod.server.shared.loader.TypeBuilderLoader
import org.rsmod.server.shared.loader.TypeEditorLoader
import org.rsmod.server.shared.loader.TypeReferencesLoader

fun main(args: Array<String>): Unit = GameServer().main(args)

class GameServer : CliktCommand(name = "server") {
    private val logger = InlineLogger()

    private val pluginPackages: Array<String>
        get() = PluginConstants.searchPackages

    override fun run() {
        val injector = createInjector()
        prepareGame(injector)
        startUpGame(injector)
    }

    fun createInjector(): Injector {
        val pluginModules = loadModules()
        val modules = Modules.combine(GameServerModule, *pluginModules.toTypedArray())
        return Guice.createInjector(modules)
    }

    fun prepareGame(injector: Injector) {
        loadCache(injector)
        loadMap(injector)
        loadTypeResolver(injector)
        loadScripts(injector)
        executeScheduledIO(injector)
    }

    private fun executeScheduledIO(injector: Injector) {
        logger.info { "Joining scheduled IO tasks..." }
        val scheduler = injector.getInstance(TaskScheduler::class.java)
        val count = scheduler.size
        val duration = measureTime {
            scheduler.joinAll()
            scheduler.clear()
        }
        reportDuration { "Executed $count IO task${if (count == 1) "" else "s"} in $duration." }
    }

    private fun loadModules(): Collection<AbstractModule> {
        logger.info { "Loading plugin modules..." }
        val modules: Collection<AbstractModule>
        val duration = measureTime {
            modules = PluginModuleLoader.load(PluginModule::class.java, pluginPackages)
        }
        reportDuration {
            "Loaded ${modules.size} plugin module${if (modules.size == 1) "" else "s"} in $duration."
        }
        return modules
    }

    private fun loadCache(injector: Injector) {
        val cachePath = injector.getInstance(Key.get(Path::class.java, GameCache::class.java))
        logger.info { "Loading cache from path: $cachePath..." }
        val store: Store
        val duration = measureTime {
            store = injector.getInstance(Key.get(Store::class.java, GameCache::class.java))
            injector.getInstance(Key.get(Cache::class.java, GameCache::class.java))
        }
        reportDuration { "Loaded cache with ${store.list().size} archives in $duration" }
    }

    private fun loadMap(injector: Injector) {
        logger.info { "Loading game map and collision flags..." }
        val duration = measureTime {
            val decoder = injector.getInstance(GameMapDecoder::class.java)
            decoder.decodeAll()
        }
        reportDuration {
            val locRegistry = injector.getInstance(LocRegistry::class.java)
            val normalZoneCount = locRegistry.mapLocs.zoneCount
            val normalLocCount = locRegistry.mapLocs.locCount()
            "Loaded ${DecimalFormat().format(normalZoneCount)} static zones and " +
                "${DecimalFormat().format(normalLocCount)} locs in $duration."
        }
    }

    private fun loadTypeResolver(injector: Injector) {
        logger.info { "Processing type resolver..." }
        val duration = measureTime {
            resolveAllTypes(injector)
            verifyTypeResolver(injector)
            cleanUpTypeResolver(injector)
        }
        logger.info { "Resolved all types in $duration." }
    }

    private fun resolveAllTypes(injector: Injector) {
        val resolver = injector.getInstance(TypeResolver::class.java)

        val references = injector.getInstance(TypeReferencesLoader::class.java)
        resolver.loadReferences(references)

        val builders = injector.getInstance(TypeBuilderLoader::class.java)
        resolver.loadBuilders(builders)

        val editors = injector.getInstance(TypeEditorLoader::class.java)
        resolver.loadEditors(editors)
    }

    private fun TypeResolver.loadReferences(loader: TypeReferencesLoader) {
        logger.debug { "Loading type references..." }
        val duration = measureTime {
            appendReferences(loader.load())
            resolveReferences()
        }
        debugDuration {
            "Loaded $referenceCount type reference${if (referenceCount == 1) "" else ""} " +
                "in $duration."
        }
    }

    private fun TypeResolver.loadBuilders(loader: TypeBuilderLoader) {
        logger.debug { "Loading type builders..." }
        val duration = measureTime {
            appendBuilders(loader.load())
            resolveBuilders()
        }
        debugDuration {
            "Loaded $builderCount type builder${if (builderCount == 1) "" else ""} in $duration."
        }
    }

    private fun TypeResolver.loadEditors(loader: TypeEditorLoader) {
        logger.debug { "Loading type editors..." }
        val duration = measureTime {
            appendEditors(loader.load())
            resolveEditors()
        }
        debugDuration {
            "Loaded $editorCount type editor${if (editorCount == 1) "" else ""} in $duration."
        }
    }

    private fun verifyTypeResolver(injector: Injector) {
        val verifier = injector.getInstance(TypeVerifier::class.java)
        val verification = verifier.verifyAll()
        if (verification.isCacheUpdateRequired()) {
            logger.debug { verification.formatError() }
            logger.info { "Packing latest cache additions and restarting server..." }
            val updater = injector.getInstance(TypeUpdater::class.java)
            updater.updateAll()
            logger.info { "Now restarting game server..." }
            return run()
        } else if (verification.isFailure()) {
            throw RuntimeException(verification.formatError())
        }
    }

    private fun cleanUpTypeResolver(injector: Injector) {
        val cleanup = injector.getInstance(TypeCleanup::class.java)
        cleanup.clearAll()
    }

    private fun loadScripts(injector: Injector) {
        logger.info { "Loading plugin scripts..." }
        val scripts: Collection<PluginScript>
        val loadDuration = measureTime {
            scripts = PluginScriptLoader.load(PluginScript::class.java, pluginPackages, injector)
        }
        val scriptContext = injector.getInstance(ScriptContext::class.java)
        val startUpDuration = measureTime {
            scripts.forEach { startUpPluginScript(it, scriptContext) }
        }
        reportDuration {
            "Loaded ${scripts.size} script${if (scripts.size == 1) "" else "s"} in " +
                "${loadDuration + startUpDuration}. " +
                "(loading took $loadDuration, startup took $startUpDuration)"
        }
    }

    private fun startUpGame(injector: Injector) {
        logger.info { "Loading server bootstrap..." }
        val bootstrap: GameBootstrap
        val duration = measureTime { bootstrap = injector.getInstance(GameBootstrap::class.java) }
        reportDuration { "Loaded server bootstrap in $duration." }
        bootstrap.startUp()
    }

    private fun reportDuration(msg: () -> String) {
        logger.info { msg() }
    }

    private fun debugDuration(msg: () -> String) {
        logger.debug { msg() }
    }

    private fun startUpPluginScript(script: PluginScript, context: ScriptContext) {
        with(script) { context.startUp() }
    }
}