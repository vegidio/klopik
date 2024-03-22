package io.vinicius.klopik.model

import kotlin.time.Duration

typealias RetryWhen = (response: Response, attempt: Int) -> Boolean

/**
 * Data class representing the options for a request.
 *
 * @property body The body of the request. This can be null if the request does not have a body.
 * @property headers The headers of the request. This can be null if the request does not have any headers.
 * @property timeout The timeout for the request. This can be null if there is no specific timeout for the request.
 * @property retries The number of retries for the request. This can be null if there are no retries specified.
 * @property retryWhen A predicate that determines when to retry the request. If this is null, the request will be
 * retried as long as the number of attempts is less or equal than the number of retries.
 */
data class RequestOptions(
    var body: String? = null,
    var headers: Map<String, String>? = null,
    var timeout: Duration? = null,
    var retries: Int? = null,
    var retryWhen: RetryWhen? = null
)