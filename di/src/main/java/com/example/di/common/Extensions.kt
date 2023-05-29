package com.example.di.common

import com.example.di.module.DiModule

@PublishedApi
@JvmSynthetic
internal fun <T> String.createKey(clazz: Class<T>): Key<T> {
    return Key(cls = clazz, qualifier = this)
}

inline fun <reified T> DiModule.single(
    qualifier: String = "",
    noinline singleLambda: (paramsHolder: ParametersHolder) -> T
) {
    this.singleProvide(T::class.java, qualifier, singleLambda)
}

inline fun <reified T> DiModule.factory(
    qualifier: String = "",
    noinline factoryLambda: (paramsHolder: ParametersHolder) -> T
) {
    this.factoryProvide(T::class.java, qualifier, factoryLambda)
}

inline fun <reified T> DiModule.get(
    qualifier: String = "",
    paramsHolder: ParametersHolder = ParametersHolder()
): T {
    val key = qualifier.createKey(T::class.java)
    return get(key = key, parametersHolder = paramsHolder)
}

inline fun <reified T> DiModule.getOrNull(
    qualifier: String = "",
    paramsHolder: ParametersHolder = ParametersHolder()
): T? {
    val key = qualifier.createKey(T::class.java)
    return getOrNull(key = key, parametersHolder = paramsHolder)
}