package me.vep.justai.handler

import me.vep.justai.dto.ConfirmationEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class Confirmator {

    @Value("\${bot.confirmationToken}")
    private lateinit var token: String

    private val logger: Logger = LoggerFactory.getLogger(Confirmator::class.java)

    fun confirm(event: ConfirmationEvent): String {
        logger.info("Successfully returned confirmation token")
        return token
    }
}
