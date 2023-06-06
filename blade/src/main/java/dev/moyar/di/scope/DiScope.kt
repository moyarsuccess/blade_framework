package dev.moyar.di.scope

import dev.moyar.di.common.DiNode
import dev.moyar.di.module.DiModule

interface DiScope : DiNode {
    val modules: MutableSet<DiModule>
    var parentScope: DiScope?
    var scopeId: String

    fun module(moduleLambda: DiModule.() -> Unit)

    fun bind(scope: DiScope)
}