package dev.moyar.di.functions

import dev.moyar.di.scope.DiScope
import dev.moyar.di.scope.DiScopeImpl

fun scope(scopeLambda: DiScope.() -> Unit): DiScope {
    val scope = DiScopeImpl()
    scopeLambda(scope)
    return scope
}

operator fun DiScope.plus(another: DiScope): List<DiScope> {
    return listOf(this, another)
}