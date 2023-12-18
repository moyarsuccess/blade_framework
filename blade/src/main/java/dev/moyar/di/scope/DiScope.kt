package dev.moyar.di.scope

import dev.moyar.di.common.DiNode
import dev.moyar.di.common.Key
import dev.moyar.di.module.DiModule

interface DiScope : DiNode {
    val modules: MutableSet<DiModule>
    var parentScope: DiScope?
    val scopeId: String

    fun <T> find(key: Key<T>): Boolean

    fun module(moduleLambda: DiModule.() -> Unit)

    fun module(module: DiModule)

    fun modules(vararg modules: DiModule)

    fun modules(modules: List<DiModule>)

    fun bind(scope: DiScope)
}
