package dev.moyar.di.common

interface DiNode {

    fun <T> provideOrNull(key: Key<T>, parametersHolder: ParametersHolder): T?

    fun <T> provide(key: Key<T>, parametersHolder: ParametersHolder): T
}
