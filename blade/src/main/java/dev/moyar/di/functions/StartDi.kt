package dev.moyar.di.functions

import android.content.Context
import dev.moyar.di.GlobalDiScope
import dev.moyar.di.common.single
import dev.moyar.di.scope.DiScope

fun startDi(context: Context, startDiLambda: DiScope.() -> Unit) {
    startDiLambda(GlobalDiScope)
    GlobalDiScope.isInitialized.set(true)
    GlobalDiScope.module { single<Context> { context } }
}

fun isDiInitialized(): Boolean {
    return GlobalDiScope.isInitialized.get()
}
