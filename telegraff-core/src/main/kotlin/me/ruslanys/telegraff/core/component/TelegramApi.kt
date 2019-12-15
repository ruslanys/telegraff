package me.ruslanys.telegraff.core.component

import me.ruslanys.telegraff.core.dto.TelegramMessage
import me.ruslanys.telegraff.core.dto.TelegramUpdate
import me.ruslanys.telegraff.core.dto.TelegramUser
import me.ruslanys.telegraff.core.dto.request.TelegramChatActionRequest
import me.ruslanys.telegraff.core.dto.request.TelegramMessageSendRequest
import me.ruslanys.telegraff.core.dto.request.TelegramPhotoSendRequest
import me.ruslanys.telegraff.core.dto.request.TelegramVoiceSendRequest

interface TelegramApi {

    fun getMe(): TelegramUser

    fun getUpdates(offset: Long?, timeout: Int?): List<TelegramUpdate>

    fun getUpdates(offset: Long?): List<TelegramUpdate> {
        return getUpdates(offset, null)
    }

    fun getUpdates(): List<TelegramUpdate> {
        return getUpdates(null)
    }

    fun setWebhook(url: String): Boolean

    fun removeWebhook(): Boolean

    fun sendMessage(request: TelegramMessageSendRequest): TelegramMessage

    fun sendPhoto(request: TelegramPhotoSendRequest): TelegramMessage

    fun sendVoice(request: TelegramVoiceSendRequest): TelegramMessage

    fun sendChatAction(request: TelegramChatActionRequest): Boolean

}
