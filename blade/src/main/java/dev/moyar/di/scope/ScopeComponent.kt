package dev.moyar.di.scope

import dev.moyar.di.GlobalDiScope

interface ScopeComponent {

    val scope: DiScope

    fun bindDi(parentScopeId: String? = null) {
        scope.parentScope = parentScopeId.getScopeFromId()
        GlobalDiScope.addToGlobalGraph(scope)
    }

    private fun String?.getScopeFromId(): DiScope? {
        if (this == null) return GlobalDiScope
        return GlobalDiScope.getScopeOrNull(this)
    }

    fun unbindDi() {
        GlobalDiScope.removeFromGlobalGraph(scope)
        scope.parentScope = null
    }
}