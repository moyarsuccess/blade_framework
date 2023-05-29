package com.example.di.scope

import com.example.di.GlobalScope

interface ScopeComponent {

    val scope: DiScope

    fun bind(parentScopeId: String? = null) {
        scope.parentScope = parentScopeId.getScopeFromId()
        GlobalScope.bindScopeToGlobalScope(scope)
    }

    private fun String?.getScopeFromId(): DiScope? {
        if (this == null) return null
        return GlobalScope.getScopeOrNull(this)
    }

    fun unbind() {
        GlobalScope.unbindScopeFromGlobalScope(scope)
        scope.parentScope = null
    }
}