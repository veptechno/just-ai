package me.vep.justai.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = UnknownEvent::class)
@JsonSubTypes(
        value = [
            JsonSubTypes.Type(value = NewMessageEvent::class, name = "message_new"),
            JsonSubTypes.Type(value = ConfirmationEvent::class, name = "confirmation"),
        ]
)
sealed class Event(
        val groupId: Int,
        val identificationKey: String
)

class NewMessageEvent(
        groupId: Int,
        secret: String,
        @JsonProperty("object")
        val content: IncomingMessageContent,
) : Event(groupId, secret)

class ConfirmationEvent(
        groupId: Int,
        secret: String
) : Event(groupId, secret)

class UnknownEvent(
        groupId: Int,
        secret: String
) : Event(groupId, secret)
