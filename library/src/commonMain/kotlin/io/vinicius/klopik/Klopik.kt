package io.vinicius.klopik

import io.vinicius.klopik.model.RequestOptions
import io.vinicius.klopik.model.Response

/**
 * The `Klopik` class is used to create an instance of an HTTP client with a base URL and default request options.
 *
 * @property baseUrl The base URL that will be prepended to all request URLs.
 * @property options A lambda function that applies default request options to all requests.
 *
 * @constructor Creates a new `Klopik` instance with the specified base URL and default request options.
 */
class Klopik(
    private val baseUrl: String = "",
    private val options: RequestOptions.() -> Unit = {}
) {
    /**
     * Sends a request to the specified URL with the specified method and options.
     *
     * @param method The HTTP method to use for the request. This is an instance of the `Method` enum.
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     * @param stream A stream callback function. If provided, the request body will be streamed using this callback
     * (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
     */
    fun request(
        method: Method,
        url: String,
        options: RequestOptions.() -> Unit = {},
        stream: StreamCallback? = null
    ): Response {
        val finalUrl = baseUrl + url
        val classOp = RequestOptions().apply(this.options)
        val funcOp = RequestOptions().apply(options)
        val mergedOptions: RequestOptions.() -> Unit = {
            body = funcOp.body ?: classOp.body
            headers = funcOp.headers?.plus(classOp.headers.orEmpty())
            timeout = funcOp.timeout ?: classOp.timeout
            retries = funcOp.retries ?: classOp.retries
            retryWhen = funcOp.retryWhen ?: classOp.retryWhen
            validate = funcOp.validate ?: classOp.validate
        }

        return Klopik.request(method, finalUrl, mergedOptions, stream)
    }

    /**
     * Sends a GET request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     * @param stream A stream callback function. If provided, the request body will be streamed using this callback
     * (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
     */
    fun get(
        url: String,
        options: RequestOptions.() -> Unit = {},
        stream: StreamCallback? = null
    ) = request(Method.Get, url, options, stream)

    /**
     * Sends a POST request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     * @param stream A stream callback function. If provided, the request body will be streamed using this callback
     * (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
     */
    fun post(
        url: String,
        options: RequestOptions.() -> Unit = {},
        stream: StreamCallback? = null
    ) = request(Method.Post, url, options, stream)

    /**
     * Sends a PUT request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     * @param stream A stream callback function. If provided, the request body will be streamed using this callback
     * (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
     */
    fun put(
        url: String,
        options: RequestOptions.() -> Unit = {},
        stream: StreamCallback? = null
    ) = request(Method.Put, url, options, stream)

    /**
     * Sends a DELETE request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     * @param stream A stream callback function. If provided, the request body will be streamed using this callback
     * (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
     */
    fun delete(
        url: String,
        options: RequestOptions.() -> Unit = {},
        stream: StreamCallback? = null
    ) = request(Method.Delete, url, options, stream)

    /**
     * Sends a PATCH request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     * @param stream A stream callback function. If provided, the request body will be streamed using this callback
     * (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
     */
    fun patch(
        url: String,
        options: RequestOptions.() -> Unit = {},
        stream: StreamCallback? = null
    ) = request(Method.Patch, url, options, stream)

    /**
     * Sends a HEAD request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     * @param stream A stream callback function. If provided, the request body will be streamed using this callback
     * (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
     */
    fun head(
        url: String,
        options: RequestOptions.() -> Unit = {},
        stream: StreamCallback? = null
    ) = request(Method.Head, url, options, stream)

    /**
     * Sends a OPTIONS request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     * @param stream A stream callback function. If provided, the request body will be streamed using this callback
     * (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws HttpException If there is an error with the request, a `HttpException` is thrown.
     */
    fun options(
        url: String,
        options: RequestOptions.() -> Unit = {},
        stream: StreamCallback? = null
    ) = request(Method.Options, url, options, stream)

    companion object
}