package com.example.di.module

import com.example.di.common.DiNode
import com.example.di.common.Key
import com.example.di.common.ParametersHolder
import com.example.di.common.TypeFactory
import com.example.di.scope.DiScope

interface DiModule : DiNode {
    val factories: HashMap<Key<*>, TypeFactory<*>>
    val diScope: DiScope?

    fun canProvide(key: Key<*>): Boolean

    fun <T> singleProvide(
        clazz: Class<T>,
        qualifier: String = "",
        singleLambda: (paramsHolder: ParametersHolder) -> T
    )

    fun <T> factoryProvide(
        clazz: Class<T>,
        qualifier: String = "",
        factoryLambda: (paramsHolder: ParametersHolder) -> T
    )
}