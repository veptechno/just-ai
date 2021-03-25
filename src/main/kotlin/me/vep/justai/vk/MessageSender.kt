package me.vep.justai.vk

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import kotlin.random.Random

@Service
class MessageSender(
    val restTemplate: RestTemplate,
    val asyncExecutor: ThreadPoolTaskExecutor
) {

    @Value("\${bot.vk.accessToken}")
    private lateinit var accessToken: String

    @Value("\${bot.groupId}")
    private var groupId: Int? = 0

    private val host = "api.vk.com"
    private val endpoint = "/method/messages.send"
    private val apiVersion = "5.80"

    private var logger: Logger = LoggerFactory.getLogger(MessageSender::class.java)

    fun send(text: String, peerId: Int) {
        if (text.isBlank()) {
            logger.error("Text must not be blank")
            throw IllegalArgumentException("Text must not be blank")
        }

        asyncExecutor.execute {
            val uri = buildUrl(text, peerId)
            restTemplate.getForEntity(uri, String::class.java)
            logger.info("Successfully sent message")
        }
    }

    private fun buildUrl(text: String, peerId: Int) =
        "https://$host$endpoint?" +
            "peer_id=$peerId" +
            "&random_id=${Random.nextInt()}" +
            "&message=$text" +
            "&group_id=$groupId" +
            "&intent=default" +
            "&access_token=$accessToken" +
            "&v=$apiVersion"
}
