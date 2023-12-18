package dev.moyar.di.scope

import dev.moyar.di.common.Key
import dev.moyar.di.module.DiModule
import dev.moyar.di.module.DiModuleImpl
import java.util.UUID

internal abstract class AbsDiScope : DiScope {
    override val modules: MutableSet<DiModule> = mutableSetOf()
    override var parentScope: DiScope? = null
    override val scopeId: String = UUID.randomUUID().toString()

    override fun module(moduleLambda: DiModule.() -> Unit) {
        val module = DiModuleImpl(this).apply(moduleLambda)
        modules.add(module)
    }

    override fun module(module: DiModule) {
        module.diScope = this
        modules.add(module)
    }

    override fun modules(vararg modules: DiModule) {
        this.modules.addAll(modules)
    }

    override fun modules(modules: List<DiModule>) {
        this.modules.addAll(modules)
    }

    override fun <T> find(key: Key<T>): Boolean {
        val couldFindInMineModules = this.modules.any { it.canProvide(key) }
        if (couldFindInMineModules) return true

        return parentScope?.find(key) ?: false
    }

    override fun bind(scope: DiScope) {
        scope.parentScope = this
    }
}
