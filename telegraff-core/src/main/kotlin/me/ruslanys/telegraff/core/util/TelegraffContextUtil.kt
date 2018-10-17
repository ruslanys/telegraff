package me.ruslanys.telegraff.core.util

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

object TelegraffContextUtil : ApplicationContextAware {

    private lateinit var context: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    fun <T> getBean(clazz: Class<T>): T {
        return context.getBean(clazz)
    }

    fun getContext(): ApplicationContext = context

}
