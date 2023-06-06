package com.example.di.scope

import com.example.di.GlobalScope
import com.example.di.common.Key
import com.example.di.common.ParametersHolder
import com.example.di.module.DiModule

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