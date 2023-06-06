package dev.moyar.di.module

import dev.moyar.di.GlobalScope
import dev.moyar.di.common.Key
import dev.moyar.di.common.ParametersHolder
import dev.moyar.di.common.TypeFactory
import dev.moyar.di.scope.DiScope

internal class DiModuleImpl(
    override val diScope: DiScope?
) : DiModule {

    override val factories: HashMap<Key<*>, TypeFactory<*>> = hashMapOf()

    override fun canProvide(key: Key<*>): Boolean {
        return factories.any { it.key == key }
    }

    override fun <T> singleProvide(
        clazz: Class<T>,
        qualifier: String,
        singleLambda: (paramsHolder: ParametersHolder) -> T
    ) {
        val typeFactory = createSingleTypeFactory(singleLambda)
        factories[Key(cls = clazz, qualifier = qualifier)] = typeFactory
    }

    override fun <T> factoryProvide(
        clazz: Class<T>,
        qualifier: String,
        factoryLambda: (paramsHolder: ParametersHolder) -> T
    ) {
        val typeFactory = object : TypeFactory<T> {
            override fun build(paramsHolder: ParametersHolder): T {
                return factoryLambda(paramsHolder)
            }
        }
        factories[Key(cls = clazz, qualifier = qualifier)] = typeFactory
    }

    @PublishedApi
    @JvmSynthetic
    internal fun <T> createSingleTypeFactory(
        lambda: (paramsHolder: ParametersHolder) -> T
    ): TypeFactory<T> {
        return object : TypeFactory<T> {
            private var obj: T? = null

            @Suppress("UNCHECKED_CAST")
            override fun build(paramsHolder: ParametersHolder): T {
                if (obj != null) return obj as T
                obj = lambda(paramsHolder)
                return obj as T
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(
        key: Key<T>,
        parametersHolder: ParametersHolder,
    ): T {
        // Ask my own factories to provide the object
        val innerObj = factories[key]?.build(parametersHolder)
        if (innerObj != null) return innerObj as T

        // Ask my scope to provide the object
        val scopeObject = diScope?.getOrNull(
            key = key,
            parametersHolder = parametersHolder
        )
        if (scopeObject != null) return scopeObject

        // Ask main graph to provide the object
        val obj = GlobalScope.get(key, parametersHolder)
        if (obj != null) return obj

        error("Type not supported $key")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> getOrNull(
        key: Key<T>,
        parametersHolder: ParametersHolder,
    ): T? {

        // Ask my own factories to provide the object
        val innerObj = factories[key]?.build(parametersHolder)
        if (innerObj != null) return innerObj as T

        // Ask my scope to provide the object
        val scopeObject = diScope?.getOrNull(
            key = key,
            parametersHolder = parametersHolder,
        )
        if (scopeObject != null) return scopeObject

        // Ask main graph to provide the object
        val obj = GlobalScope.get(key, parametersHolder)
        if (obj != null) return obj

        return null
    }
}