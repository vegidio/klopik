package io.vinicius.klopik.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Data class representing the options for a request.
 *
 * @property body The body of the request. It can be null if the request does not have a body.
 * @property headers The headers of the request represented as a map where the key is the header name and the value is
 * the header value. It can be null if the request does not have headers.
 * @property timeout The timeout for the request represented as a Duration. The default value is 5 seconds.
 */
data class RequestOptions(
    var body: String? = null,
    var headers: Map<String, String>? = null,
    var timeout: Duration = 5.seconds
)