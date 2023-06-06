package dev.moyar.di.scope

import dev.moyar.di.GlobalScope

interface ScopeComponent {

    val scope: (scopeId: String) -> DiScope
    val scopeComponentId: String

    fun bindDi(parentScopeId: String? = null) {
        val scope = scope.invoke(scopeComponentId)
        scope.parentScope = parentScopeId.getScopeFromId()
        GlobalScope.addToGlobalGraph(scope)
    }

    private fun String?.getScopeFromId(): DiScope? {
        if (this == null) return null
        return GlobalScope.getScopeOrNull(this)
    }

    fun unbindDi() {
        val scope = GlobalScope.getScopeOrNull(scopeComponentId) ?: return
        GlobalScope.removeFromGlobalGraph(scope)
        scope.parentScope = null
    }
}