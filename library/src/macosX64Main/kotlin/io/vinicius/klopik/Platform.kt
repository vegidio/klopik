@file:OptIn(ExperimentalForeignApi::class)

package io.vinicius.klopik

import io.vinicius.klopik.model.RequestOptions
import io.vinicius.klopik.model.Response
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.cstr
import kotlinx.cinterop.toKString
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.posix.memcpy

internal actual fun platformRequest(
    method: Method,
    url: String,
    options: RequestOptions.() -> Unit
): Response {
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

        Response(
            body = byteArray,
            length = r1,
            httpCode = r2.toShort(),
            headers = resHeaders ?: emptyMap(),
            error = error?.let { KlopikException(url, it) }
        )
    }
}