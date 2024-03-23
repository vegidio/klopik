package io.vinicius.klopik

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class KlopikTest {
    private val klopik = Klopik()

    @Test
    fun `request with valid parameters returns expected response`() {
        val response = klopik.request(Method.Get, "https://httpbin.org/get")
        assertEquals(200, response.statusCode)
    }

    @Test
    fun `request with valid parameters and options returns expected response`() {
        val response = klopik.request(Method.Get, "https://httpbin.org/get") {
            headers = mapOf("Accept" to "application/json")
        }
        assertEquals(200, response.statusCode)
    }

    @Test
    fun `request with invalid URL throws exception`() {
        assertFailsWith<KlopikException> {
            klopik.request(Method.Get, "invalid_url")
        }
    }

    @Test
    fun `request to an non-existent URL throws exception`() {
        val exception = assertFailsWith<KlopikException> {
            klopik.request(Method.Get, "https://httpbin.org/status/404")
        }
        assertEquals(404, exception.statusCode)
    }
}