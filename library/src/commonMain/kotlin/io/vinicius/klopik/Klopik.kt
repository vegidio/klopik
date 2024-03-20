package io.vinicius.klopik

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.cstr
import kotlinx.cinterop.toKString
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
class Klopik {
    companion object {
        /**
         * Sends a HTTP request.
         *
         * @param method The HTTP method to use for the request; this should be an instance of the `Method` enum.
         * @param url The URL to send the request to.
         * @param body The body of the request; this is optional and defaults to `null`.
         * @param headers The headers to include with the request; this is optional and defaults to `null`.
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun request(
            method: Method,
            url: String,
            body: String? = null,
            headers: Map<String, Any>? = null
        ): Response {
            val result = klopik.Request(method.value.cstr, url.cstr, body?.cstr, null)

            return result.useContents {
                val byteArray = try {
                    ByteArray(r1).apply { usePinned { memcpy(it.addressOf(0), r0, r1.convert()) } }
                } catch (_: Exception) {
                    byteArrayOf()
                }

                val error = r4?.toKString()

                Response(
                    body = byteArray,
                    length = r1,
                    httpCode = r2.toShort(),
                    headers = emptyMap(),
                    error = error?.let { KlopikException(it) }
                )
            }
        }

        /**
         * Sends a GET request to the specified URL.
         *
         * @param url The URL to send the request to.
         * @param body The body of the request; this is optional and defaults to `null`.
         * @param headers The headers to include with the request; this is optional and defaults to `null`.
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun get(
            url: String,
            body: String? = null,
            headers: Map<String, Any>? = null
        ): Response = request(Method.Get, url, body, headers)

        /**
         * Sends a POST request to the specified URL.
         *
         * @param url The URL to send the request to.
         * @param body The body of the request; this is optional and defaults to `null`.
         * @param headers The headers to include with the request; this is optional and defaults to `null`.
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun post(
            url: String,
            body: String? = null,
            headers: Map<String, Any>? = null
        ): Response = request(Method.Post, url, body, headers)
    }
}