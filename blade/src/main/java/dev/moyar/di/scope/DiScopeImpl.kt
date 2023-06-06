package dev.moyar.di.scope

import dev.moyar.di.GlobalScope
import dev.moyar.di.common.Key
import dev.moyar.di.common.ParametersHolder
import dev.moyar.di.module.DiModule

internal class DiScopeImpl(
    override var scopeId: String
) : AbsDiScope() {

    override fun <T> get(key: Key<T>, parametersHolder: ParametersHolder): T {
        // Ask other same scope level modules
        val sameScopeModulesObject: T? = modules.getOrNull(key, parametersHolder)
        if (sameScopeModulesObject != null) return sameScopeModulesObject

        // Ask the parent scope to provide object
        val parentScopeObject =
            (parentScope as? DiScopeImpl)?.getOrNull(key, parametersHolder)
        if (parentScopeObject != null) return parentScopeObject

        val obj = GlobalScope.getOrNull(key, parametersHolder)
        if (obj != null) return obj

        error("No object found for key $key")
    }

    override fun <T> getOrNull(key: Key<T>, parametersHolder: ParametersHolder): T? {
        // Ask other same scope level modules
        val sameScopeModulesObject: T? = modules.getOrNull(key, parametersHolder)
        if (sameScopeModulesObject != null) return sameScopeModulesObject

        // Ask the parent scope to provide object
        val parentScopeObject =
            (parentScope as? DiScopeImpl)?.getOrNull(key, parametersHolder)
        if (parentScopeObject != null) return parentScopeObject

        val obj = GlobalScope.getOrNull(key, parametersHolder)
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