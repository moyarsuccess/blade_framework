package dev.moyar.blade.common

import android.util.Log

fun log(text: String) {
    Log.i("WTF", text)
}

fun Any.getInstanceId(): String {
    return toString().split("@").getOrNull(1) ?: ""
}