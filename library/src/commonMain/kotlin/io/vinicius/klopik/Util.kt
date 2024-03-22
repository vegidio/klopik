package io.vinicius.klopik

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Serializes the given Map of headers into a JSON string.
 *
 * @param headers A Map where each key is a header name and the value is the corresponding header value.
 * @return A JSON string of headers where each key is a header name and the value is the corresponding header value.
 */
internal fun serializeHeaders(headers: Map<String, String>): String {
    return Json.encodeToString(headers)
}

/**
 * Deserializes the given JSON string of headers into a Map.
 *
 * @param headers A JSON string of headers where each key is a header name and the value is a list of header values.
 * @return A Map where each key is a header name and the value is a string of all header values for that key, joined by
 * commas.
 */
internal fun deserializeHeaders(headers: String): Map<String, String> {
    val temp: Map<String, List<String>> = Json.decodeFromString(headers)
    return temp.mapValues { (_, value) -> value.joinToString(", ") }
}