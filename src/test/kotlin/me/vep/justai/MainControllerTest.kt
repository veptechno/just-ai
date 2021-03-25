package me.vep.justai

import me.vep.justai.vk.OK
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
class MainControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Value("\${bot.groupId}")
    var groupId = 0

    @Value("\${bot.vk.identificationKey}")
    lateinit var identificationKey: String

    @Test
    fun `returns OK when an unknown event is received`() {
        val json = """
            {
                "type": "group_join",
                "object": {},
                "group_id": $groupId,
                "secret": "$identificationKey"
            }
            """.trimIndent()

        mockMvc.post("/echobot") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isOk() }
            content { string(OK) }
        }
    }

    @Test
    fun `returns FORBIDDEN if the group_id is not equal to the group id`() {
        val invalidGroupId = 1204235235
        val json = """
            {
                "type": "confirmation",
                "group_id": $invalidGroupId,
                "secret": "$identificationKey"
            }
            """.trimIndent()

        mockMvc.post("/echobot") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status {
                isForbidden()
                reason("Invalid group id")
            }
        }
    }

    @Test
    fun `returns FORBIDDEN if the secret is not equal to the vk identification key`() {
        val invalidKey = "jdsfnu34ir3i4fn34f2klgnjn5"
        val json = """
            {
                "type": "confirmation",
                "group_id": $groupId,
                "secret": "$invalidKey"
            }
            """.trimIndent()

        mockMvc.post("/echobot") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status {
                isForbidden()
                reason("Invalid identification key (secret)")
            }
        }
    }

    @Test
    fun `returns FORBIDDEN due to the secret if the secret and the group_id are invalid`() {
        val invalidKey = "jdsfnu34ir3i4fn34f2klgnjn5"
        val invalidGroupId = 1204235235
        val json = """
            {
                "type": "confirmation",
                "group_id": $invalidGroupId,
                "secret": "$invalidKey"
            }
            """.trimIndent()

        mockMvc.post("/echobot") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status {
                isForbidden()
                reason("Invalid identification key (secret)")
            }
        }
    }
}
