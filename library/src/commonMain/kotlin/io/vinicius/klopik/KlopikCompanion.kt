package io.vinicius.klopik

import io.vinicius.klopik.exception.HttpException
import io.vinicius.klopik.model.RequestOptions
import io.vinicius.klopik.model.Response

/**
 * Sends a request to the specified URL with the specified method and options.
 *
 * @param method The HTTP method to use for the request. This is an instance of the `Method` enum.
 * @param url The URL to send the request to.
 * @param options A lambda function with the request options. This object is used to specify the body, headers and other
 * request parameters (optional).
 * @param stream A stream callback function. If provided, the request body will be streamed using this callback
 * (optional).
 *
 * @return A `Response` object containing the response from the server.
 *
 * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
 */
fun Klopik.Companion.request(
    method: Method,
    url: String,
    options: RequestOptions.() -> Unit = {},
    stream: StreamCallback? = null
): Response {
    val op = RequestOptions().apply(options)
    var response: Response
    var error: String?
    var attempt = 0

    // Retry logic
    val retries = op.retries ?: 0
    val retryWhen = op.retryWhen ?: { _, _ -> true }

    do {
        val (r, e) = if (stream == null) {
            platformRequest(method, url, options)
        } else {
            platformStream(method, url, options, stream)
        }

        response = r
        error = e
        val shouldRetry = attempt++ < retries && retryWhen(response, attempt)
    } while (shouldRetry)

    // Validate the response
    val isValid = op.validate ?: { it.statusCode in 200..299 }
    if (!isValid(response)) {
        throw HttpException(
            url = url,
            body = response.body,
            length = response.length,
            statusCode = response.statusCode,
            headers = response.headers,
            message = error ?: "The response did not pass the validation check."
        )
    }

    return response
}

/**
 * Sends a GET request to the specified URL.
 *
 * @param url The URL to send the request to.
 * @param options A lambda function with the request options. This object is used to specify the body, headers and other
 * request parameters (optional).
 * @param stream A stream callback function. If provided, the request body will be streamed using this callback
 * (optional).
 *
 * @return A `Response` object containing the response from the server.
 *
 * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
 */
fun Klopik.Companion.get(
    url: String,
    options: RequestOptions.() -> Unit = {},
    stream: StreamCallback? = null
) = request(Method.Get, url, options, stream)

/**
 * Sends a POST request to the specified URL.
 *
 * @param url The URL to send the request to.
 * @param options A lambda function with the request options. This object is used to specify the body, headers and other
 * request parameters (optional).
 * @param stream A stream callback function. If provided, the request body will be streamed using this callback
 * (optional).
 *
 * @return A `Response` object containing the response from the server.
 *
 * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
 */
fun Klopik.Companion.post(
    url: String,
    options: RequestOptions.() -> Unit = {},
    stream: StreamCallback? = null
) = request(Method.Post, url, options, stream)

/**
 * Sends a PUT request to the specified URL.
 *
 * @param url The URL to send the request to.
 * @param options A lambda function with the request options. This object is used to specify the body, headers and other
 * request parameters (optional).
 * @param stream A stream callback function. If provided, the request body will be streamed using this callback
 * (optional).
 *
 * @return A `Response` object containing the response from the server.
 *
 * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
 */
fun Klopik.Companion.put(
    url: String,
    options: RequestOptions.() -> Unit = {},
    stream: StreamCallback? = null
) = request(Method.Put, url, options, stream)

/**
 * Sends a DELETE request to the specified URL.
 *
 * @param url The URL to send the request to.
 * @param options A lambda function with the request options. This object is used to specify the body, headers and other
 * request parameters (optional).
 * @param stream A stream callback function. If provided, the request body will be streamed using this callback
 * (optional).
 *
 * @return A `Response` object containing the response from the server.
 *
 * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
 */
fun Klopik.Companion.delete(
    url: String,
    options: RequestOptions.() -> Unit = {},
    stream: StreamCallback? = null
) = request(Method.Delete, url, options, stream)

/**
 * Sends a PATCH request to the specified URL.
 *
 * @param url The URL to send the request to.
 * @param options A lambda function with the request options. This object is used to specify the body, headers and other
 * request parameters (optional).
 * @param stream A stream callback function. If provided, the request body will be streamed using this callback
 * (optional).
 *
 * @return A `Response` object containing the response from the server.
 *
 * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
 */
fun Klopik.Companion.patch(
    url: String,
    options: RequestOptions.() -> Unit = {},
    stream: StreamCallback? = null
) = request(Method.Patch, url, options, stream)

/**
 * Sends a HEAD request to the specified URL.
 *
 * @param url The URL to send the request to.
 * @param options A lambda function with the request options. This object is used to specify the body, headers and other
 * request parameters (optional).
 * @param stream A stream callback function. If provided, the request body will be streamed using this callback
 * (optional).
 *
 * @return A `Response` object containing the response from the server.
 *
 * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
 */
fun Klopik.Companion.head(
    url: String,
    options: RequestOptions.() -> Unit = {},
    stream: StreamCallback? = null
) = request(Method.Head, url, options, stream)

/**
 * Sends a OPTIONS request to the specified URL.
 *
 * @param url The URL to send the request to.
 * @param options A lambda function with the request options. This object is used to specify the body, headers and other
 * request parameters (optional).
 * @param stream A stream callback function. If provided, the request body will be streamed using this callback
 * (optional).
 *
 * @return A `Response` object containing the response from the server.
 *
 * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
 */
fun Klopik.Companion.options(
    url: String,
    options: RequestOptions.() -> Unit = {},
    stream: StreamCallback? = null
) = request(Method.Options, url, options, stream)