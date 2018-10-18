package me.ruslanys.telegraff.sample

import org.springframework.stereotype.Component
import java.util.*

@Component
class NameGenerator {

    private val random = Random()

    fun generateName(length: Int): String {
        val name = StringBuilder()
        for (i in 0 until length) {
            val index = Math.abs(random.nextInt() % DICTIONARY.size)
            val char = if (name.isEmpty()) DICTIONARY[index].toUpperCase() else DICTIONARY[index]
            name.append(char)
        }
        return name.toString()
    }

    companion object {
        private val DICTIONARY: CharArray = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'v', 'x', 'y', 'z')
    }

}