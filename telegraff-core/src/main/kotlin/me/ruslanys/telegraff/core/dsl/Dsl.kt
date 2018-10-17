package me.ruslanys.telegraff.core.dsl

import me.ruslanys.telegraff.core.dto.request.TelegramSendRequest
import me.ruslanys.telegraff.core.exception.HandlerException
import me.ruslanys.telegraff.core.util.TelegraffContextUtil
import org.springframework.context.ApplicationContext


fun handler(vararg commands: String, init: HandlerBuilder.() -> Unit): Handler {
    val builder = HandlerBuilder(commands.asList())
    init(builder) // handler.init()

    return builder.build()
}

class HandlerBuilder(private val commands: List<String>) {

    private val stepBuilders: MutableList<StepBuilder<*>> = arrayListOf()

    private var process: ProcessBlock? = null

    fun <T> step(key: String, init: StepBuilder<T>.() -> Unit): StepBuilder<T> {
        val builder = StepBuilder<T>(key)
        init(builder) // step.init()

        stepBuilders.add(builder)

        return builder
    }

    fun process(processor: ProcessBlock) {
        this.process = processor
    }

    internal fun build(): Handler {
        val steps = arrayListOf<Step<*>>()

        for (i in 0 until stepBuilders.size) {
            val builder = stepBuilders[i]
            val step = builder.build {
                if (i + 1 < stepBuilders.size) {
                    stepBuilders[i + 1].key
                } else {
                    null
                }
            }
            steps.add(step)
        }


        return Handler(
                commands,
                steps.associateBy { it.key },
                stepBuilders.firstOrNull()?.key,
                process ?: throw HandlerException("Process block must not be null!")
        )
    }

}

class StepBuilder<T>(val key: String) {

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

fun context(): ApplicationContext = TelegraffContextUtil.getContext()

fun <T> getBean(clazz: Class<T>): T = TelegraffContextUtil.getBean(clazz)


typealias ProcessBlock = (state: HandlerState, answers: Map<String, Any>) -> TelegramSendRequest?

typealias QuestionBlock = (HandlerState) -> TelegramSendRequest
typealias ValidationBlock<T> = (String) -> T
typealias NextStepBlock = (HandlerState) -> String?
