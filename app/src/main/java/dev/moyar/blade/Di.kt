package dev.moyar.blade

import dev.moyar.di.common.single
import dev.moyar.di.functions.module
import dev.moyar.di.functions.scope

val applicationModule = module {
    single { "base_url" }
}
val mainActivityScope = scope {
    module(applicationModule)
    module {
        single {

        }
    }
}
