package com.example.di.functions

import com.example.di.scope.DiScope
import com.example.di.scope.DiScopeImpl

fun scope(scopeComponentId: String, scopeLambda: DiScope.() -> Unit): DiScope {
    val scope = DiScopeImpl(scopeComponentId)
    scopeLambda(scope)
    return scope
}

operator fun DiScope.plus(another: DiScope): List<DiScope> {
    return listOf(this, another)
}