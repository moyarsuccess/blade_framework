package com.example.di.lazy

import java.io.Serializable

@Suppress("SerialVersionUIDInSerializableClass")
internal class InitializedLazyImpl<out T>(override val value: T) : Lazy<T>, Serializable {

    override fun isInitialized(): Boolean = true

    override fun toString(): String = value.toString()
}
