package dev.moyar.di.module

import dev.moyar.di.common.DiNode
import dev.moyar.di.common.Key
import dev.moyar.di.common.ParametersHolder
import dev.moyar.di.common.TypeFactory
import dev.moyar.di.scope.DiScope

interface DiModule : DiNode {
    val factories: HashMap<Key<*>, TypeFactory<*>>
    var diScope: DiScope?

    fun canProvide(key: Key<*>): Boolean

    fun <T> singleProvide(
        clazz: Class<T>,
        qualifier: String = "",
        singleLambda: (paramsHolder: ParametersHolder) -> T,
    )

    fun <T> factoryProvide(
        clazz: Class<T>,
        qualifier: String = "",
        factoryLambda: (paramsHolder: ParametersHolder) -> T,
    )
}
