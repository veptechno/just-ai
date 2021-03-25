package me.vep.justai.handler

import me.vep.justai.vk.MessageSender
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.Mockito.timeout
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate


@SpringBootTest
@AutoConfigureMockMvc
class MessageSenderTest {

    @MockBean
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var sender: MessageSender

    @BeforeEach
    fun beforeEach() {
        `when`(restTemplate.getForEntity(anyString(), any(String::class.java::class.java)))
            .thenReturn(ResponseEntity.ok(""))
    }

    @Test
    fun `sends a message with filled text`() {
        sender.send("hello", 1234567)

        verify(restTemplate, timeout(100))
            .getForEntity(anyString(), any(String::class.java::class.java))
    }

    @Test
    fun `throws an exception and does not send a message if the text is blank`() {
        assertThrows<IllegalArgumentException> {
            sender.send("  ", 1232143)
        }

        verify(restTemplate, never())
            .getForEntity(anyString(), any(String::class.java::class.java))
    }
}
