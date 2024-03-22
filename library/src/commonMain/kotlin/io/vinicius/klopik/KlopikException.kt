package io.vinicius.klopik

data class KlopikException(
    val url: String,
    override val message: String
) : Exception()