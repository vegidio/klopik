package io.vinicius.klopik.model

import io.vinicius.klopik.KlopikException
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString

/**
 * This is a data class represents the response from a network request.
 *
 * @property body The body of the response, represented as a ByteArray.
 * @property length The length of the response body.
 * @property httpCode The HTTP status code of the response.
 * @property headers The headers of the response, represented as a Map where the key is the header name and the value is
 * the header value.
 * @property error An optional KlopikException that represents any error that occurred while making the request.
 */
@OptIn(ExperimentalForeignApi::class)
data class Response(
    val body: ByteArray,
    val length: Int,
    val httpCode: Short,
    val headers: Map<String, String>,
    val error: KlopikException?
) {
    /**
     * Converts the body of the response, which is a ByteArray, to a String (UTF-8). This is useful for cases where the
     * response body is expected to be text.
     */
    val text = body.toKString()

    /**
     * Checks if the HTTP status code of the response is within the range 200 to 299. This range is typically used to
     * indicate successful HTTP requests. If the status code is within this range, 'ok' will be true. Otherwise, it will
     * be false.
     */
    val ok = httpCode in 200..299

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other as Response

        if (!body.contentEquals(other.body)) return false
        if (length != other.length) return false
        if (httpCode != other.httpCode) return false
        if (headers != other.headers) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = body.contentHashCode()
        result = 31 * result + length.hashCode()
        result = 31 * result + httpCode.hashCode()
        result = 31 * result + headers.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }
}
