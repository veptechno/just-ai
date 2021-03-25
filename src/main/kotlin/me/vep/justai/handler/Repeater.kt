package me.vep.justai.handler

import me.vep.justai.dto.NewMessageEvent
import me.vep.justai.vk.MessageSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class Repeater(
        val messageSender: MessageSender
) {

    private var logger: Logger = LoggerFactory.getLogger(Repeater::class.java)

    fun process(event: NewMessageEvent): String {
        val inputText = event.content.text

        if (inputText.isBlank()) {
            logger.info("Text in the message is blank")
            return "ok"
        }

        messageSender.send("Вы сказали: $inputText", event.content.peerId)
        return "ok"
    }
}
