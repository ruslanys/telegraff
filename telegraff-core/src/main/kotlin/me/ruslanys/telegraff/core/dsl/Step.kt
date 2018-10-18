package me.ruslanys.telegraff.core.dsl

class Step<T : Any>(
        val key: String,
        val question: QuestionBlock,
        val validation: ValidationBlock<T>,
        val next: NextStepBlock
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Step<*>) return false

        if (key != other.key) return false

        return true
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

}