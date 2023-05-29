package com.example.di

import com.example.di.common.Key
import com.example.di.common.ParametersHolder
import com.example.di.module.DiModule
import com.example.di.scope.AbsDiScope
import com.example.di.scope.DiScope

@PublishedApi
internal object GlobalScope : AbsDiScope() {

    private val directGlobalScopes = mutableMapOf<String, DiScope>()

    fun bindScopeToGlobalScope(scope: DiScope) {
        directGlobalScopes[scope.scopeId] = scope
    }

    fun unbindScopeFromGlobalScope(scope: DiScope) {
        directGlobalScopes.remove(scope.scopeId)
    }

    fun getScopeOrNull(scopeId: String): DiScope? {
        return directGlobalScopes[scopeId]
    }

    fun addModule(module: DiModule) {
        modules.add(module)
    }

    override fun <T> getOrNull(key: Key<T>, parametersHolder: ParametersHolder): T? {
        return modules
            .firstOrNull { it.canProvide(key) }
            ?.getOrNull(key, parametersHolder)
    }

    override fun <T> get(key: Key<T>, parametersHolder: ParametersHolder): T {
        return modules
            .firstOrNull { it.canProvide(key) }
            ?.get(key, parametersHolder)
            ?: error("No value found for type $key")
    }
}