package io.vinicius.klopik

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StreamTest {
    private val klopik = Klopik()

    @Test
    fun `request with valid parameters returns expected stream`() {
        val response = klopik.request(Method.Get, "https://httpbin.org/get") {
            assertTrue(it.size > 200)
        }
        assertEquals(200, response.statusCode)
    }
}