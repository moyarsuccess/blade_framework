package dev.moyar.di.scope

import dev.moyar.di.module.DiModule
import dev.moyar.di.module.DiModuleImpl

internal abstract class AbsDiScope : DiScope {
    override val modules: MutableSet<DiModule> = mutableSetOf()

    override var parentScope: DiScope? = null

    override fun module(moduleLambda: DiModule.() -> Unit) {
        val module = DiModuleImpl(this).apply(moduleLambda)
        modules.add(module)
    }

    override fun bind(scope: DiScope) {
        scope.parentScope = this
    }
}