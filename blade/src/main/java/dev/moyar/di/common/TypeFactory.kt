package dev.moyar.di.common

interface TypeFactory<T> {

    fun build(paramsHolder: ParametersHolder): T
}