package io.vinicius.klopik

data class Response(
    val body: ByteArray = byteArrayOf(),
    val length: Int = 0,
    val httpCode: Short = 0,
    val headers: Map<String, Any> = emptyMap(),
    val error: KlopikException? = null
) {
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
