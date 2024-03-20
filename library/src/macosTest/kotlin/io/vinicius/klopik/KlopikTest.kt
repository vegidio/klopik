package io.vinicius.klopik

import kotlin.test.Test

class KlopikTest {
    @Test
    fun `HTTP GET result is 200 OK`() {
        val response = Klopik.get("https://httpbin.org/get")
        println(response)
    }
}