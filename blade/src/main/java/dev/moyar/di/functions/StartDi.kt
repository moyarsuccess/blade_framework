package dev.moyar.di.functions

import dev.moyar.di.GlobalDiScope
import dev.moyar.di.scope.DiScope

fun startDi(startDiLambda: DiScope.() -> Unit) {
    startDiLambda(GlobalDiScope)
    GlobalDiScope.isInitialized.set(true)
}

fun isDiInitialized(): Boolean {
    return GlobalDiScope.isInitialized.get()
}
