package dev.moyar.di.functions

import dev.moyar.di.GlobalDiScope
import dev.moyar.di.module.DiModule

fun startDi(vararg modules: DiModule) {
    startDi(modules.toList())
}

fun startDi(modules: List<DiModule>) {
    modules.forEach { GlobalDiScope.addModule(it) }
}
