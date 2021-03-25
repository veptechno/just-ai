package me.vep.justai

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class ConfirmRequestTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Value("\${bot.groupId}")
    var groupId = 0

    @Value("\${bot.vk.identificationKey}")
    lateinit var identificationKey: String

    @Value("\${bot.confirmationToken}")
    lateinit var correctToken: String

    @Test
    fun `Returns confirmation token when an confirmation event is received`() {
        val json =
            """{
                    "type": "confirmation",
                    "group_id": $groupId,
                    "secret": "$identificationKey"
                }""".trimIndent()

        mockMvc.post("/echobot") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isOk() }
            content { string(correctToken) }
        }
    }
}
