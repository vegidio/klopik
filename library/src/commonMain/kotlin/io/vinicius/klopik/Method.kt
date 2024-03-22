package io.vinicius.klopik

/**
 * Enum class representing HTTP methods.
 *
 * @property value The string representation of the HTTP method.
 */
enum class Method(val value: String) {
    Get("GET"),
    Post("POST"),
    Put("PUT"),
    Delete("DELETE"),
    Patch("PATCH"),
    Head("HEAD"),
    Options("OPTIONS")
}