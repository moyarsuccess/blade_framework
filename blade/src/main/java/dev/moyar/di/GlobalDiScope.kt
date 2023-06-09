package dev.moyar.di

import dev.moyar.di.common.Key
import dev.moyar.di.common.ParametersHolder
import dev.moyar.di.module.DiModule
import dev.moyar.di.scope.AbsDiScope
import dev.moyar.di.scope.DiScope
import java.util.UUID

@PublishedApi
internal object GlobalDiScope : AbsDiScope() {

    private val directGlobalScopes = mutableMapOf<String, DiScope>()
    override var scopeId: String = UUID.randomUUID().toString()

    fun addToGlobalGraph(scope: DiScope) {
        directGlobalScopes[scope.scopeId] = scope
    }

    fun removeFromGlobalGraph(scope: DiScope) {
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