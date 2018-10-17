package me.ruslanys.telegraff.core.dsl

class Handler(
        val commands: List<String>,
        private val steps: Map<String, Step<*>>,
        private val initialStepKey: String?,
        val process: ProcessBlock
) {

    fun getInitialStep(): Step<*>? {
        return if (initialStepKey != null) {
            steps[initialStepKey]!!
        } else {
            null
        }
    }

    fun getStepByKey(key: String): Step<*>? {
        return steps[key]
    }

}