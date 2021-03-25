package me.vep.justai.dto

data class IncomingMessageContent(
        val id: Int,
        val peerId: Int,
        val fromId: Int,
        val text: String
)
