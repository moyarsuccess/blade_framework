package com.example.di.common

interface TypeFactory<T> {

    fun build(paramsHolder: ParametersHolder): T
}