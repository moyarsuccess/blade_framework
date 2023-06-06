package dev.moyar.blade

import android.app.Application
import dev.moyar.di.functions.startDi

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startDi(mainModule)
    }
}