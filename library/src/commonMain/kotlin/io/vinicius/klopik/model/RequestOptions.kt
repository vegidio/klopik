package io.vinicius.klopik.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Data class representing the options for a request.
 *
 * @property body The body of the request. This can be null if the request does not have a body.
 * @property headers The headers of the request. This can be null if the request does not have any headers.
 * @property timeout The timeout for the request. This can be null if there is no specific timeout for the request.
 */
data class RequestOptions(
    var body: String? = null,
    var headers: Map<String, String>? = null,
    var timeout: Duration? = null
)