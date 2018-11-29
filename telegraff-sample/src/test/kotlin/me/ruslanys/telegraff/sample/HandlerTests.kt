package me.ruslanys.telegraff.sample

import me.ruslanys.telegraff.core.dsl.*
import me.ruslanys.telegraff.core.dto.TelegramChat
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.support.GenericApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [ HandlerTests.Config::class ])
abstract class HandlerTests(private val handlerCommand: String) {

    @Autowired
    private lateinit var handlersFactory: HandlersFactory

    protected lateinit var handler: Handler
    protected lateinit var state: HandlerState

    @Before
    fun setUp() {
        handler = handlersFactory.getHandlers()[handlerCommand]!!
        state = HandlerState(getTelegramChat(), handler)
    }

    protected open fun getTelegramChat(): TelegramChat =
            TelegramChat(-1L, "PRIVATE", null, "bot", "First name", "Last name")

    @Suppress("UNCHECKED_CAST")
    protected fun <T: Any> getStep(key: String): Step<T> {
        return (handler.getStepByKey(key) as Step<T>?)!!
    }

    @TestConfiguration
    class Config {

        @Bean
        fun handlersFactory(context: GenericApplicationContext): HandlersFactory {
            return DefaultHandlersFactory(context, "handlers")
        }

    }

}