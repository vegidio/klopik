package io.vinicius.klopik

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs

class KlopikTest {
    private val klopik = Klopik()

    @Test
    fun `request with valid parameters returns expected response`() {
        val response = klopik.request(Method.Get, "https://httpbin.org/get")
        assertEquals(200, response.httpCode)
    }

    @Test
    fun `request with invalid URL throws exception`() {
        val response = klopik.request(Method.Get, "invalid_url")
        assertIs<KlopikException>(response.error)
    }

    @Test
    fun `request with valid parameters and options returns expected response`() {
        val response = klopik.request(Method.Get, "https://httpbin.org/get") {
            headers = mapOf("Accept" to "application/json")
        }
        assertEquals(200, response.httpCode)
    }

    @Test
    fun `request to an non-existent URL is not OK`() {
        val response = klopik.request(Method.Get, "https://httpbin.org/status/404")
        assertFalse(response.isOk)
    }
}