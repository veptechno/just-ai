package me.vep.justai.router

import me.vep.justai.dto.ConfirmationEvent
import me.vep.justai.dto.Event
import me.vep.justai.dto.NewMessageEvent
import me.vep.justai.dto.UnknownEvent
import me.vep.justai.handler.Confirmator
import me.vep.justai.handler.Repeater
import me.vep.justai.vk.OK
import org.springframework.stereotype.Component

@Component
class EventRouter(
    private val confirmator: Confirmator,
    private val repeater: Repeater
) {

    fun route(event: Event) = when (event) {
        is NewMessageEvent -> repeater.process(event)
        is ConfirmationEvent -> confirmator.confirm(event)
        is UnknownEvent -> OK
    }
}
