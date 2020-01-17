package me.ruslanys.telegraff.core.x

import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine


//@ExperimentalCoroutinesApi
//private val channel: BroadcastChannel<Message> = BroadcastChannel(10)

private val storage = hashMapOf<String, Continuation<*>>()

@ExperimentalCoroutinesApi
fun main() = runBlocking<Unit> {

    val f = GlobalScope.launch(CoroutineName("ruslanys")) {
        val locationFrom = ask("Откуда поедем?")
        val locationTo = ask("Куда поедем?")
        val paymentMethod = ask("Чем платишь?", "Картой", "Наличкой")

        println("Заказ принят. Поедем из $locationFrom в $locationTo. Оплата $paymentMethod.")
    }

//    val s = GlobalScope.launch(CoroutineName("Ivan")) {
//        val answer = ask("Name?")
//        println("#2: $answer")
//    }

    GlobalScope.launch {
        while (true) {
            val line = Scanner(System.`in`).nextLine()
            val parts = line.split(":")


            (storage[parts[0]] as? Continuation<Any>)?.resumeWith(Result.success(parts[1]))
//            channel.send(Message(parts[0], parts[1]))
        }
    }

    f.join()
//    s.join()

}

suspend fun ask(question: String, vararg answers: String): String {
    val name = coroutineContext[CoroutineName]!!.name

    return if (answers.isNotEmpty()) {
        var text: String
        do {
            text = askInternal(name, question, *answers)
        } while (!answers.contains(text))
        text
    } else {
        askInternal(name, question, *answers)
    }
}

private suspend fun askInternal(name: String, question: String, vararg answers: String): String {
    if (answers.isEmpty()) {
        println(question)
    } else {
        println("$question ${answers.toList()}")
    }

    return suspendCoroutine {
        storage[name] = it
    }
}

data class Message(val author: String, val text: String)
