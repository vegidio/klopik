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
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
     * message.
     */
    fun request(
        method: Method,
        url: String,
        options: RequestOptions.() -> Unit = {}
    ): Response {
        val finalUrl = baseUrl + url
        val classOp = RequestOptions().apply(this.options)
        val funcOp = RequestOptions().apply(options)
        val mergedOptions: RequestOptions.() -> Unit = {
            body = funcOp.body ?: classOp.body
            headers = funcOp.headers?.plus(classOp.headers.orEmpty())
            timeout = funcOp.timeout ?: classOp.timeout
            retries = funcOp.retries ?: classOp.retries
            retryWhen = funcOp.retryWhen ?: classOp.retryWhen ?: { _, _ -> true }
        }

        return Klopik.request(method, finalUrl, mergedOptions)
    }

    /**
     * Sends a GET request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
     * message.
     */
    fun get(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Get, url, options)

    /**
     * Sends a POST request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
     * message.
     */
    fun post(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Post, url, options)

    /**
     * Sends a PUT request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
     * message.
     */
    fun put(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Put, url, options)

    /**
     * Sends a DELETE request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
     * message.
     */
    fun delete(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Delete, url, options)

    /**
     * Sends a PATCH request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
     * message.
     */
    fun patch(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Patch, url, options)

    /**
     * Sends a HEAD request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
     * message.
     */
    fun head(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Head, url, options)

    /**
     * Sends a OPTIONS request to the specified URL.
     *
     * @param url The URL to send the request to.
     * @param options A lambda function with the request options. This object is used to specify the body, headers
     * and other request parameters (optional).
     *
     * @return A `Response` object containing the response from the server.
     *
     * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
     * message.
     */
    fun options(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Options, url, options)

    companion object {
        /**
         * Sends a request to the specified URL with the specified method and options.
         *
         * @param method The HTTP method to use for the request. This is an instance of the `Method` enum.
         * @param url The URL to send the request to.
         * @param options A lambda function with the request options. This object is used to specify the body, headers
         * and other request parameters (optional).
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun request(
            method: Method,
            url: String,
            options: RequestOptions.() -> Unit = {}
        ): Response {
            val op = RequestOptions().apply(options)
            var response: Response
            var attempt = 0

            // Retry logic
            do {
                response = platformRequest(method, url, options)
                val shouldRetry = attempt++ < (op.retries ?: 0) && op.retryWhen?.invoke(response, attempt) ?: true
            } while (shouldRetry)

            return response
        }

        /**
         * Sends a GET request to the specified URL.
         *
         * @param url The URL to send the request to.
         * @param options A lambda function with the request options. This object is used to specify the body, headers
         * and other request parameters (optional).
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun get(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Get, url, options)

        /**
         * Sends a POST request to the specified URL.
         *
         * @param url The URL to send the request to.
         * @param options A lambda function with the request options. This object is used to specify the body, headers
         * and other request parameters (optional).
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun post(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Post, url, options)

        /**
         * Sends a PUT request to the specified URL.
         *
         * @param url The URL to send the request to.
         * @param options A lambda function with the request options. This object is used to specify the body, headers
         * and other request parameters (optional).
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun put(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Put, url, options)

        /**
         * Sends a DELETE request to the specified URL.
         *
         * @param url The URL to send the request to.
         * @param options A lambda function with the request options. This object is used to specify the body, headers
         * and other request parameters (optional).
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun delete(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Delete, url, options)

        /**
         * Sends a PATCH request to the specified URL.
         *
         * @param url The URL to send the request to.
         * @param options A lambda function with the request options. This object is used to specify the body, headers
         * and other request parameters (optional).
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun patch(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Patch, url, options)

        /**
         * Sends a HEAD request to the specified URL.
         *
         * @param url The URL to send the request to.
         * @param options A lambda function with the request options. This object is used to specify the body, headers
         * and other request parameters (optional).
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun head(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Head, url, options)

        /**
         * Sends a OPTIONS request to the specified URL.
         *
         * @param url The URL to send the request to.
         * @param options A lambda function with the request options. This object is used to specify the body, headers
         * and other request parameters (optional).
         *
         * @return A `Response` object containing the response from the server.
         *
         * @throws KlopikException If there is an error with the request, a `KlopikException` is thrown with the error
         * message.
         */
        fun options(url: String, options: RequestOptions.() -> Unit = {}) = request(Method.Options, url, options)
    }
}