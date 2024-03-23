package io.vinicius.klopik.model

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString

/**
 * This is a data class represents the response from a network request.
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
