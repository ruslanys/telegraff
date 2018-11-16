package me.ruslanys.telegraff.core.dsl

import me.ruslanys.telegraff.core.dto.request.TelegramSendRequest
import me.ruslanys.telegraff.core.exception.HandlerException
import org.springframework.context.support.GenericApplicationContext


fun handler(vararg commands: String, init: HandlerDsl.() -> Unit): HandlerDslWrapper {
    return { context ->
        val dsl = HandlerDsl(commands.asList(), context)
        init(dsl) // handler.init()

        dsl.build()
    }
}

class HandlerDsl(private val commands: List<String>, val context: GenericApplicationContext) {

    private val stepDsls: MutableList<StepDsl<*>> = arrayListOf()
    private var process: ProcessBlock? = null


    fun <T : Any> step(key: String, init: StepDsl<T>.() -> Unit): StepDsl<T> {
        val dsl = StepDsl<T>(key)
        init(dsl) // step.init()

        stepDsls.add(dsl)

        return dsl
    }

    fun process(processor: ProcessBlock) {
        this.process = processor
    }

    internal fun build(): Handler {
        val steps = arrayListOf<Step<*>>()

        for (i in 0 until stepDsls.size) {
            val builder = stepDsls[i]
            val step = builder.build {
                if (i + 1 < stepDsls.size) {
                    stepDsls[i + 1].key
                } else {
                    null
                }
            }
            steps.add(step)
        }


        return Handler(
                commands,
                steps.associateBy { it.key },
                stepDsls.firstOrNull()?.key,
                process ?: throw HandlerException("Process block must not be null!")
        )
    }

    inline fun <reified T> getBean(): T {
        return context.getBean(T::class.java)
    }

    fun readClasspathResource(path: String): ByteArray = javaClass.classLoader.getResourceAsStream(path).use {
        it.readBytes()
    }

}

class StepDsl<T : Any>(val key: String) {

    private var question: QuestionBlock? = null

    private var validation: ValidationBlock<T>? = null
    private var next: NextStepBlock? = null


    fun question(question: QuestionBlock) {
        this.question = question
    }

    fun validation(validation: ValidationBlock<T>) {
        this.validation = validation
    }

    fun next(next: NextStepBlock) {
        this.next = next
    }

    internal fun build(defaultNext: NextStepBlock): Step<T> {
        return Step(
                key,
                question ?: throw HandlerException("Step question must not be null!"),
                validation ?: {
                    @Suppress("UNCHECKED_CAST")
                    it as T
                },
                next ?: defaultNext
        )
    }

}


typealias ProcessBlock = (state: HandlerState, answers: Map<String, Any>) -> TelegramSendRequest?

typealias QuestionBlock = (HandlerState) -> TelegramSendRequest
typealias ValidationBlock<T> = (String) -> T
typealias NextStepBlock = (HandlerState) -> String?
typealias HandlerDslWrapper = (GenericApplicationContext) -> Handler