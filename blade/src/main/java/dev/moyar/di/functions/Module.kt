package dev.moyar.di.functions

import dev.moyar.di.module.DiModule
import dev.moyar.di.module.DiModuleImpl

fun module(moduleLambda: DiModule.() -> Unit): DiModule {
    val module = DiModuleImpl(null)
    moduleLambda(module)
    return module
}

operator fun DiModule.plus(another: DiModule): List<DiModule> {
    return listOf(this, another)
}
