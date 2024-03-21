package io.vinicius.klopik

import kotlin.test.Test
import kotlin.test.assertEquals

class UtilTest {
    @Test
    fun `serializeHeaders returns JSON string for non-empty map`() {
        val headers = mapOf("Content-Type" to "application/json", "Authorization" to "Bearer token")
        val expected = """{"Content-Type":"application/json","Authorization":"Bearer token"}"""
        assertEquals(expected, serializeHeaders(headers))
    }

    @Test
    fun `serializeHeaders returns empty JSON object for empty map`() {
        val headers = emptyMap<String, String>()
        val expected = "{}"
        assertEquals(expected, serializeHeaders(headers))
    }

    @Test
    fun `deserializeHeaders returns map for non-empty JSON string`() {
        val headers = """{"Content-Type":["application/json"],"Authorization":["Bearer token"]}"""
        val expected = mapOf("Content-Type" to "application/json", "Authorization" to "Bearer token")
        assertEquals(expected, deserializeHeaders(headers))
    }

    @Test
    fun `deserializeHeaders returns map for multiple JSON string values`() {
        val headers = """{"Content-Type":["application/json","application/xml"],"Authorization":["Bearer token"]}"""
        val expected = mapOf("Content-Type" to "application/json, application/xml", "Authorization" to "Bearer token")
        assertEquals(expected, deserializeHeaders(headers))
    }

    @Test
    fun `deserializeHeaders returns empty map for empty JSON string`() {
        val headers = "{}"
        val expected = emptyMap<String, String>()
        assertEquals(expected, deserializeHeaders(headers))
    }
}