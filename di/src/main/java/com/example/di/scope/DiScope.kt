package com.example.di.scope

import com.example.di.common.DiNode
import com.example.di.module.DiModule

interface DiScope : DiNode {
    val modules: MutableSet<DiModule>
    var parentScope: DiScope?
    var scopeId: String

    fun module(moduleLambda: DiModule.() -> Unit)

    fun bind(scope: DiScope)
}