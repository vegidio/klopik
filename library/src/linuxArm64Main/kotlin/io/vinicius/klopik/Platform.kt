@file:OptIn(ExperimentalForeignApi::class)

package io.vinicius.klopik

import io.vinicius.klopik.model.RequestOptions
import io.vinicius.klopik.model.Response
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.cstr
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.posix.memcpy

internal actual fun platformRequest(
    method: Method,
    url: String,
    options: RequestOptions.() -> Unit
): Pair<Response, String?> {
    val op = RequestOptions().apply(options)
    val result = klopik.Request(
        method = method.value.cstr,
        url = url.cstr,
        body = op.body?.cstr,
        headers = op.headers?.let { serializeHeaders(it).cstr }
    )

    return result.useContents {
        val byteArray = try {
            ByteArray(r1).apply { usePinned { memcpy(it.addressOf(0), r0, r1.convert()) } }
        } catch (_: Exception) {
            byteArrayOf()
        }

        val error = r4?.toKString()
        val resHeaders = r3?.let { deserializeHeaders(it.toKString()) }
        val response = Response(
            body = byteArray,
            length = r1,
            statusCode = r2.toShort(),
            headers = resHeaders ?: emptyMap()
        )

        Pair(response, error)
    }
}

internal actual fun platformStream(
    method: Method,
    url: String,
    options: RequestOptions.() -> Unit,
    stream: (ByteArray) -> Unit
): Pair<Response, String?> {
    val cCallback = staticCFunction { chunk: COpaquePointer?, size: Int ->
        val byteArray = try {
            ByteArray(size).apply { usePinned { memcpy(it.addressOf(0), chunk, size.convert()) } }
        } catch (_: Exception) {
            byteArrayOf()
        }

        stream(byteArray)
    }

    val op = RequestOptions().apply(options)
    val result = klopik.Stream(
        method = method.value.cstr,
        url = url.cstr,
        body = op.body?.cstr,
        headers = op.headers?.let { serializeHeaders(it).cstr },
        callback = cCallback
    )

    return result.useContents {
        val error = r3?.toKString()
        val resHeaders = r2?.let { deserializeHeaders(it.toKString()) }
        val response = Response(
            body = byteArrayOf(),
            length = r0,
            statusCode = r1.toShort(),
            headers = resHeaders ?: emptyMap()
        )

        Pair(response, error)
    }
}