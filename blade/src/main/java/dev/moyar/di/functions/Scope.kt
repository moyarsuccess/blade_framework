package dev.moyar.di.functions

import dev.moyar.di.scope.DiScope
import dev.moyar.di.scope.DiScopeImpl

fun scope(scopeComponentId: String, scopeLambda: DiScope.() -> Unit): DiScope {
    val scope = DiScopeImpl(scopeComponentId)
    scopeLambda(scope)
    return scope
}

operator fun DiScope.plus(another: DiScope): List<DiScope> {
    return listOf(this, another)
}