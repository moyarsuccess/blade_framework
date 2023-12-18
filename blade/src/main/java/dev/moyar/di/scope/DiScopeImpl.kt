package dev.moyar.di.scope

import dev.moyar.di.GlobalDiScope
import dev.moyar.di.common.Key
import dev.moyar.di.common.ParametersHolder
import dev.moyar.di.module.DiModule

internal class DiScopeImpl : AbsDiScope() {

    override fun <T> provide(key: Key<T>, parametersHolder: ParametersHolder): T {
        // Ask other same scope level modules
        val sameScopeModulesObject: T? = modules.getOrNull(key, parametersHolder)
        if (sameScopeModulesObject != null) return sameScopeModulesObject

        // Ask the parent scope to provide object
        val parentScopeObject = parentScope?.provideOrNull(key, parametersHolder)
        if (parentScopeObject != null) return parentScopeObject

        val obj = GlobalDiScope.provideOrNull(key, parametersHolder)
        if (obj != null) return obj

        error("No object found for key $key")
    }

    override fun <T> provideOrNull(key: Key<T>, parametersHolder: ParametersHolder): T? {
        // Ask other same scope level modules
        val sameScopeModulesObject: T? = modules.getOrNull(key, parametersHolder)
        if (sameScopeModulesObject != null) return sameScopeModulesObject

        // Ask the parent scope to provide object
        val parentScopeObject = parentScope?.provideOrNull(key, parametersHolder)
        if (parentScopeObject != null) return parentScopeObject

        val obj = GlobalDiScope.provideOrNull(key, parametersHolder)
        if (obj != null) return obj

        return null
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> Set<DiModule>.getOrNull(
        key: Key<T>,
        paramsHolder: ParametersHolder = ParametersHolder(),
    ): T? {
        return firstOrNull { it.canProvide(key) }
            ?.factories
            ?.get(key)
            ?.build(paramsHolder) as T?
    }
}
