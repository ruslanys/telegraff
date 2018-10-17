package me.ruslanys.telegraff.core.client

import me.ruslanys.telegraff.core.dto.TelegramUpdate

interface TelegramClient {

    fun start()

    fun shutdown()

    fun onUpdate(update: TelegramUpdate)

}