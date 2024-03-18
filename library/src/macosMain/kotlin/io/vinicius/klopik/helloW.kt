@file:OptIn(ExperimentalForeignApi::class)

package io.vinicius.klopik

import klopik.Get
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cstr
import kotlinx.cinterop.toKString
import kotlinx.cinterop.useContents

@OptIn(ExperimentalForeignApi::class)
fun helloW(): String {
    val result = Get("https://httpbin.org/get".cstr, null, null)
    val (response, error) = result.useContents {
        Pair(r0?.toKString(), r1?.toKString())
    }

    println("Resp: $response")
    println("Error: $error")

    return "blah"
}