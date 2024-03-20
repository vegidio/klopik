package io.vinicius.klopik

enum class Method(val value: String) {
    Get("GET"),
    Post("POST"),
    Put("PUT"),
    Delete("DELETE"),
    Patch("PATCH"),
    Head("HEAD"),
    Options("OPTIONS")
}