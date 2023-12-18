package dev.moyar.di

import dev.moyar.di.common.Key
import dev.moyar.di.common.ParametersHolder
import dev.moyar.di.module.DiModule
import dev.moyar.di.scope.AbsDiScope
import dev.moyar.di.scope.DiScope
import java.util.UUID
import java.util.concurrent.atomic.AtomicBoolean

@PublishedApi
internal object GlobalDiScope : AbsDiScope() {

    internal val isInitialized = AtomicBoolean(false)
    private val directGlobalScopes = mutableMapOf<String, DiScope>()
    override val scopeId: String = UUID.randomUUID().toString()

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

    override fun <T> provideOrNull(key: Key<T>, parametersHolder: ParametersHolder): T? {
        return modules
            .firstOrNull { it.canProvide(key) }
            ?.provideOrNull(key, parametersHolder)
    }

    override fun <T> provide(key: Key<T>, parametersHolder: ParametersHolder): T {
        return modules
            .firstOrNull { it.canProvide(key) }
            ?.provide(key, parametersHolder)
            ?: error("No value found for type $key")
    }

    fun clear() {
        modules.clear()
        directGlobalScopes.clear()
    }
}
