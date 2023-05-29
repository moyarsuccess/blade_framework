package com.example.di.functions

import com.example.di.GlobalScope
import com.example.di.module.DiModule

fun startDi(vararg modules: DiModule) {
    startDi(modules.toList())
}

fun startDi(modules: List<DiModule>) {
    modules.forEach { GlobalScope.addModule(it) }
}
