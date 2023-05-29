package com.example.myapplication

import com.example.di.common.get
import com.example.di.common.single
import com.example.di.functions.module
import com.example.di.functions.scope

val customWidgetScope = scope {
    module { single { Test1(get()) } }
    module { single { Test2(get()) } }
}

val playerActivityScope = scope {
    module { single { Test2(get()) } }
}

val frg1Scope = scope {
    module { single { Test2(get()) } }
}

val frg2Scope = scope {
    module { single { Test2(get()) } }
}

val mainModule = module {
    single { "Str1" }
    single { Test0(get()) }
}