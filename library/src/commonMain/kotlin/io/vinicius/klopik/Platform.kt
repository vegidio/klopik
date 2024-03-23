package io.vinicius.klopik

import io.vinicius.klopik.model.RequestOptions
import io.vinicius.klopik.model.Response

internal expect fun platformRequest(
    method: Method,
    url: String,
    options: RequestOptions.() -> Unit = {}
): Pair<Response, String?>

internal expect fun platformStream(
    method: Method,
    url: String,
    options: RequestOptions.() -> Unit = {},
    stream: (ByteArray) -> Unit
): Pair<Response, String?>