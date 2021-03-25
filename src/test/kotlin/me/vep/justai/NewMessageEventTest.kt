package me.vep.justai

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.web.client.RestTemplate

@SpringBootTest
@AutoConfigureMockMvc
class NewMessageEventTest {

    @MockBean
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var mockMvc: MockMvc

    @Value("\${bot.id}")
    var botId = 0

    @Value("\${bot.vk.identificationKey}")
    lateinit var identificationKey: String

    @BeforeEach
    fun beforeEach() {
        `when`(restTemplate.getForEntity(anyString(), any(String::class.java::class.java)))
            .thenReturn(ResponseEntity.ok(""))
    }

    @Test
    fun `Returns OK and does not send a message when new message event is received with blank text`() {
        val json =
            """{
                    "type": "message_new",
                    "object": {
                        "id": 1,
                        "peer_id": 123,
                        "from_id": 345,
                        "text": ""
                    },
                    "group_id": $botId,
                    "secret": "$identificationKey"
                }""".trimIndent()

        mockMvc.post("/echobot") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isOk() }
            content { string("ok") }
        }
        verify(restTemplate, never())
            .getForEntity(anyString(), any(String::class.java::class.java))
    }

    @Test
    fun `Returns OK and sends a message when new message event is received with filled text`() {
        val json =
            """{
                    "type": "message_new",
                    "object": {
                        "id": 1,
                        "peer_id": 123,
                        "from_id": 345,
                        "text": "hello"
                    },
                    "group_id": $botId,
                    "secret": "$identificationKey"
                }""".trimIndent()

        mockMvc.post("/echobot") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isOk() }
            content { string("ok") }
        }
        verify(restTemplate, times(1))
            .getForEntity(anyString(), any(String::class.java::class.java))
    }
}
