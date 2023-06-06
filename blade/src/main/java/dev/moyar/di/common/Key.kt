package dev.moyar.di.common

data class Key<T>(
    val cls: Class<T>,
    val qualifier: String = "",
)
