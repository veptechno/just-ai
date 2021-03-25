package me.vep.justai.controller

import me.vep.justai.dto.Event
import me.vep.justai.router.EventRouter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/echobot")
class MainController(
    private val router: EventRouter
) {

    @Value("\${bot.vk.identificationKey}")
    private lateinit var vkIdentificationKey: String

    @Value("\${bot.groupId}")
    private var currentGroupId: Int = 0

    private var logger: Logger = LoggerFactory.getLogger(MainController::class.java)

    @PostMapping
    fun post(@RequestBody event: Event): String {
        logger.info("Received new event ${event::class}")
        validate(event)
        return router.route(event)
    }

    private fun validate(event: Event) {
        if (event.identificationKey != vkIdentificationKey) {
            logger.warn("Event is not certified. Received identification key = ${event.identificationKey}")
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid identification key (secret)")
        }

        if (event.groupId != currentGroupId) {
            logger.warn("Event is incorrectly addressed. Group id = ${event.groupId}")
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid group id")
        }
    }
}
