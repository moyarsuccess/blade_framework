package com.example.myapplication

import android.app.Application
import com.example.di.functions.startDi

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startDi(mainModule)
    }
}