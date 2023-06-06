package dev.moyar.di.common

interface DiNode {
    fun <T> getOrNull(key: Key<T>, parametersHolder: ParametersHolder): T?

    fun <T> get(key: Key<T>, parametersHolder: ParametersHolder): T
}