package io.vinicius.klopik.model

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import kotlinx.serialization.json.Json

/**
 * This data class represents the response from a network request.
 *
 * @property body The body of the response, represented as a ByteArray.
 * @property length The length of the response body.
 * @property statusCode The HTTP status code of the response.
 * @property headers The headers of the response, represented as a Map where the key is the header name and the value is
 * the header value.
 */
@OptIn(ExperimentalForeignApi::class)
data class Response(
    val body: ByteArray,
    val length: Int,
    val statusCode: Short,
    val headers: Map<String, String>
) {
    /**
     * Converts the body of the response, which is a ByteArray, to a String (UTF-8). This is useful for cases where the
     * response body is expected to be text.
     */
    val text = body.toKString()

    /**
     * This function is used to deserialize the JSON response body into an object of type `T`.
     * The function is inline and reified, which means it can safely use the generic type `T` at runtime.
     *
     * @return An object of type `T` that represents the deserialized JSON response body.
     * @throws kotlinx.serialization.SerializationException If the given JSON string cannot be deserialized into an
     * object of type `T`.
     */
    inline fun <reified T> deserialize(): T = Json.decodeFromString(text)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other as Response

        if (!body.contentEquals(other.body)) return false
        if (length != other.length) return false
        if (statusCode != other.statusCode) return false
        if (headers != other.headers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = body.contentHashCode()
        result = 31 * result + length.hashCode()
        result = 31 * result + statusCode.hashCode()
        result = 31 * result + headers.hashCode()
        return result
    }
}
