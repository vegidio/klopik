package io.vinicius.klopik.exception

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString

@OptIn(ExperimentalForeignApi::class)
data class HttpException(
    val url: String,
    val body: ByteArray,
    val length: Int,
    val statusCode: Short,
    val headers: Map<String, String>,
    override val message: String
) : Exception() {
    /**
     * Converts the body of the response, which is a ByteArray, to a String (UTF-8). This is useful for cases where the
     * response body is expected to be text.
     */
    val text = body.toKString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other as HttpException

        if (url != other.url) return false
        if (!body.contentEquals(other.body)) return false
        if (length != other.length) return false
        if (statusCode != other.statusCode) return false
        if (headers != other.headers) return false
        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + body.contentHashCode()
        result = 31 * result + length.hashCode()
        result = 31 * result + statusCode.hashCode()
        result = 31 * result + headers.hashCode()
        result = 31 * result + message.hashCode()
        return result
    }
}