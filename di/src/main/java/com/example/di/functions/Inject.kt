package com.example.di.functions

import com.example.di.GlobalScope
import com.example.di.common.DiParameter
import com.example.di.common.ParametersHolder
import com.example.di.common.createKey
import com.example.di.lazy.SynchronizedLazyImpl
import com.example.di.scope.ScopeComponent

fun parametersOf(vararg params: DiParameter): ParametersHolder {
    return ParametersHolder(params.toMutableList())
}

inline fun <reified T> inject(
    qualifier: String = "",
    paramsHolder: ParametersHolder = ParametersHolder(),
): T {
    val key = qualifier.createKey(clazz = T::class.java)
    return GlobalScope
        .get(key, paramsHolder)
        ?: error("No value found for type ${T::class.java}")
}

inline fun <reified T> ScopeComponent.inject(
    qualifier: String = "",
    paramsHolder: ParametersHolder = ParametersHolder(),
): T {
    val key = qualifier.createKey(clazz = T::class.java)
    return scope.getOrNull(key, paramsHolder)
        ?: error("No value found for type ${T::class.java}")
}

@Suppress("SwallowedException")
inline fun <reified T> injectOrNull(
    qualifier: String = "",
    paramsHolder: ParametersHolder = ParametersHolder(),
): T? {
    val key = qualifier.createKey(clazz = T::class.java)
    return GlobalScope.get<T>(key, paramsHolder)
}

@Suppress("SwallowedException")
inline fun <reified T> ScopeComponent.injectOrNull(
    qualifier: String = "",
    paramsHolder: ParametersHolder = ParametersHolder(),
): T? {
    val key = qualifier.createKey(clazz = T::class.java)
    return scope.getOrNull(key, paramsHolder)
}

inline fun <reified T> lazyInject(
    qualifier: String = "",
    paramsHolder: ParametersHolder = ParametersHolder(),
    lock: Any? = null,
): Lazy<T> = SynchronizedLazyImpl(
    initializer = {
        inject(qualifier = qualifier, paramsHolder = paramsHolder)
    },
    lock = lock,
)

inline fun <reified T> ScopeComponent.lazyInject(
    qualifier: String = "",
    paramsHolder: ParametersHolder = ParametersHolder(),
    lock: Any? = null,
): Lazy<T> = SynchronizedLazyImpl(
    initializer = {
        inject(qualifier = qualifier, paramsHolder = paramsHolder)
    },
    lock = lock,
)
