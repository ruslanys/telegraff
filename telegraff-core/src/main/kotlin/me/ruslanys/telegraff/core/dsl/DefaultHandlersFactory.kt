package me.ruslanys.telegraff.core.dsl

import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngineFactory
import org.jetbrains.kotlin.utils.addToStdlib.measureTimeMillisWithResult
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileFilter
import javax.script.ScriptEngineFactory

@Component
class DefaultHandlersFactory(scenariosPath: String) : HandlersFactory {

    private val handlers: MutableMap<String, Handler> = hashMapOf()

    init {
        val factory: ScriptEngineFactory = KotlinJsr223JvmLocalScriptEngineFactory()
        val scenarios = File(javaClass.classLoader.getResource(scenariosPath).toURI())
                .listFiles(FileFilter {
                    it.extension == "kts"
                })

        for (scenarioFile in scenarios) {
            val handler = compile(factory, scenarioFile)
            addHandler(handler)
        }
    }

    private fun compile(factory: ScriptEngineFactory, file: File): Handler {
        val scriptEngine = factory.scriptEngine

        val compiled = measureTimeMillisWithResult {
            scriptEngine.eval(file.bufferedReader()) as Handler
        }

        log.info("Compilation for ${file.nameWithoutExtension} took ${compiled.first} ms")

        return compiled.second
    }

    private fun addHandler(handler: Handler) {
        for (command in handler.commands) {
            val previousValue = handlers.put(command, handler)
            if (previousValue != null) {
                throw IllegalArgumentException("$command is already in use.")
            }
        }
    }

    override fun getHandlers(): Map<String, Handler> {
        return handlers
    }

    companion object {
        private val log = LoggerFactory.getLogger(DefaultHandlersFactory::class.java)
    }
}