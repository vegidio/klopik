@file:OptIn(ExperimentalForeignApi::class)

package io.vinicius.klopik

import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.posix.memcpy

typealias StreamCallback = (ByteArray) -> Unit

internal object CallbackHandler {
    private var streamCallback: StreamCallback? = null

    fun setStreamCallback(callback: StreamCallback) {
        streamCallback = callback
    }

    fun clearStreamCallback() {
        streamCallback = null
    }

    fun handleStream(chunk: COpaquePointer?, size: Int) {
        val byteArray = try {
            ByteArray(size).apply { usePinned { memcpy(it.addressOf(0), chunk, size.convert()) } }
        } catch (_: Exception) {
            byteArrayOf()
        }

        streamCallback?.invoke(byteArray)
    }
}