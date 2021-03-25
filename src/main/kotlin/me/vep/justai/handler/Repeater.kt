package me.vep.justai.handler

import me.vep.justai.dto.NewMessageEvent
import me.vep.justai.vk.MessageSender
import me.vep.justai.vk.OK
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
            return OK
        }

        messageSender.send("Вы сказали: $inputText", event.content.peerId)
        return OK
    }
}
