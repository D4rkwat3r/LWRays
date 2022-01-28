package com.darkwater.lwrays

import com.darkwater.lwrays.fragments.RaidFragment
import com.darkwater.lwrays.models.Message
import com.darkwater.lwrays.models.MessageSendingRequest
import com.darkwater.lwrays.network.APIClient
import com.darkwater.lwrays.utils.SessionHolder
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.WebSocket

class WorkerThread(
    private val webSocket: WebSocket,
    private val threadId: Long,
    private val messageText: String,
    private val mapper: ObjectMapper,
) : Thread() {

    private var isStopRequested: Boolean = false

    private fun workerCycle() {
        while (!isStopRequested) {
            val text = if (States.PREMIUM) {
                messageText
            } else {
                "$messageText\n\nИспользуется приложение LWRays\nАвтор: https://t.me/D4rkwat3r\nЕго канал: https://t.me/DWReaction"
            }
            val request = mapper.writeValueAsString(
                MessageSendingRequest(
                    1,
                    threadId,
                    Message(
                        1,
                        1,
                        threadId,
                        SessionHolder.extractUid()!!,
                        text,
                    )
                )
            )
            webSocket.send(request)
            sleep((1..10).random().toLong())
        }
    }
    override fun run() {
        workerCycle()
    }

    fun kill() {
        isStopRequested = true
    }

}