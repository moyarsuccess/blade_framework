package dev.moyar.di.scope

import dev.moyar.di.module.DiModule
import dev.moyar.di.module.DiModuleImpl
import java.util.UUID

internal abstract class AbsDiScope : DiScope {
    override val modules: MutableSet<DiModule> = mutableSetOf()
    override var scopeId: String = UUID.randomUUID().toString()
    override var parentScope: DiScope? = null

    override fun module(moduleLambda: DiModule.() -> Unit) {
        val module = DiModuleImpl(this).apply(moduleLambda)
        modules.add(module)
    }

    override fun bind(scope: DiScope) {
        scope.parentScope = this
    }
}