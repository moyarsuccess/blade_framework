package com.example.di.scope

import com.example.di.module.DiModule
import com.example.di.module.DiModuleImpl
import java.util.UUID

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