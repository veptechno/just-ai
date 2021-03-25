package me.vep.justai.router

import me.vep.justai.dto.Event
import org.springframework.stereotype.Component

@Component
class EventRouter() {

    fun route(event: Event): String {
        return ""
    }
}
