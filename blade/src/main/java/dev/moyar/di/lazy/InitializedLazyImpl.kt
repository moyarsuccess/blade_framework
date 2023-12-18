package dev.moyar.di.lazy

import java.io.Serializable

internal class InitializedLazyImpl<out T>(override val value: T) : Lazy<T>, Serializable {

    override fun isInitialized(): Boolean = true

    override fun toString(): String = value.toString()

    companion object {
        const val serialVersionUID = 1L
    }
}
