package dev.moyar.blade

import dev.moyar.di.common.get
import dev.moyar.di.common.single
import dev.moyar.di.functions.module
import dev.moyar.di.functions.scope
import dev.moyar.di.scope.DiScope

val customWidgetScope: DiScope
    get() = scope {
        module { single { Test1(get()) } }
        module { single { Test2(get()) } }
    }

val playerActivityScope: DiScope
    get() = scope {
        module { single { Test2(get()) } }
    }

val frg1Scope: DiScope
    get() = scope {
        module { single { Test2(get()) } }
    }

val frg2Scope: DiScope
    get() = scope {
        module { single { Test2(get()) } }
    }

val mainModule = module {
    single { "Str1" }
    single { Test0(get()) }
}