package com.example.myapplication

import com.example.di.common.get
import com.example.di.common.single
import com.example.di.functions.module
import com.example.di.functions.scope

fun customWidgetScope(scopeComponentId: String) = scope(scopeComponentId) {
    module { single { Test1(get()) } }
    module { single { Test2(get()) } }
}

fun playerActivityScope(scopeComponentId: String) = scope(scopeComponentId) {
    module { single { Test2(get()) } }
}

fun frg1Scope(scopeComponentId: String) = scope(scopeComponentId) {
    module { single { Test2(get()) } }
}

fun frg2Scope(scopeComponentId: String) = scope(scopeComponentId) {
    module { single { Test2(get()) } }
}

val mainModule = module {
    single { "Str1" }
    single { Test0(get()) }
}