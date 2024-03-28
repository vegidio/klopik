package io.vinicius.klopik.model

import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@Serializable
data class TestObject(val name: String, val value: Int)

class ResponseTest {
    @Test
    fun `should convert body to string`() {
        val body = "Hello, World!".encodeToByteArray()
        val response = Response(body, body.size, 200, emptyMap())
        assertEquals("Hello, World!", response.text)
    }

    @Test
    fun `should deserialize json body`() {
        val body = """{"name":"test","value":123}""".encodeToByteArray()
        val response = Response(body, body.size, 200, emptyMap())
        val obj = response.deserialize<TestObject>()
        assertEquals(TestObject("test", 123), obj)
    }

    @Test
    fun `should not be equal with different bodies`() {
        val body1 = "Hello, World!".encodeToByteArray()
        val body2 = "Goodbye, World!".encodeToByteArray()
        val response1 = Response(body1, body1.size, 200, emptyMap())
        val response2 = Response(body2, body2.size, 200, emptyMap())
        assertNotEquals(response1, response2)
    }

    @Test
    fun `should not be equal with different headers`() {
        val body = "Hello, World!".encodeToByteArray()
        val headers1 = mapOf("Content-Type" to "text/plain")
        val headers2 = mapOf("Content-Type" to "application/json")
        val response1 = Response(body, body.size, 200, headers1)
        val response2 = Response(body, body.size, 200, headers2)
        assertNotEquals(response1, response2)
    }

    @Test
    fun `should have different hashcodes for different bodies`() {
        val body1 = "Hello, World!".encodeToByteArray()
        val body2 = "Goodbye, World!".encodeToByteArray()
        val response1 = Response(body1, body1.size, 200, emptyMap())
        val response2 = Response(body2, body2.size, 200, emptyMap())
        assertNotEquals(response1.hashCode(), response2.hashCode())
    }

    @Test
    fun `should have different hashcodes for different headers`() {
        val body = "Hello, World!".encodeToByteArray()
        val headers1 = mapOf("Content-Type" to "text/plain")
        val headers2 = mapOf("Content-Type" to "application/json")
        val response1 = Response(body, body.size, 200, headers1)
        val response2 = Response(body, body.size, 200, headers2)
        assertNotEquals(response1.hashCode(), response2.hashCode())
    }
}