package dev.moyar.di.functions

import dev.moyar.di.GlobalScope
import dev.moyar.di.module.DiModule

fun startDi(vararg modules: DiModule) {
    startDi(modules.toList())
}

fun startDi(modules: List<DiModule>) {
    modules.forEach { GlobalScope.addModule(it) }
}
