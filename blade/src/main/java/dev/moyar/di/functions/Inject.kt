package dev.moyar.di.functions

import dev.moyar.di.GlobalScope
import dev.moyar.di.common.DiParameter
import dev.moyar.di.common.ParametersHolder
import dev.moyar.di.common.createKey
import dev.moyar.di.lazy.SynchronizedLazyImpl
import dev.moyar.di.scope.ScopeComponent

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
    val scope = GlobalScope.getScopeOrNull(scopeComponentId)
    return scope?.getOrNull(key, paramsHolder)
        ?: error("No value found for type ${T::class.java}")
}

@Suppress("SwallowedException")
inline fun <reified T> injectOrNull(
    qualifier: String = "",
    paramsHolder: ParametersHolder = ParametersHolder(),
): T? {
    val key = qualifier.createKey(clazz = T::class.java)
    return GlobalScope.getOrNull(key, paramsHolder)
}

@Suppress("SwallowedException")
inline fun <reified T> ScopeComponent.injectOrNull(
    qualifier: String = "",
    paramsHolder: ParametersHolder = ParametersHolder(),
): T? {
    val key = qualifier.createKey(clazz = T::class.java)
    val scope = GlobalScope.getScopeOrNull(scopeComponentId)
    return scope?.getOrNull(key, paramsHolder)
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
