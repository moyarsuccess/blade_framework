package com.example.di.functions

import com.example.di.module.DiModule
import com.example.di.module.DiModuleImpl

fun module(moduleLambda: DiModule.() -> Unit): DiModule {
    val module = DiModuleImpl(null)
    moduleLambda(module)
    return module
}

operator fun DiModule.plus(another: DiModule): List<DiModule> {
    return listOf(this, another)
}
