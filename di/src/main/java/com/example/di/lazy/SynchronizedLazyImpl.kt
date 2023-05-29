package com.example.di.lazy

import java.io.Serializable

@Suppress("SerialVersionUIDInSerializableClass")
@PublishedApi
internal class SynchronizedLazyImpl<out T>(initializer: () -> T, lock: Any? = null) : Lazy<T>, Serializable {
    private var initializer: (() -> T)? = initializer

    @Volatile
    private var _value: Any? = UninitializedValue

    // final field is required to enable safe publication of constructed instance
    private val lock = lock ?: this

    override val value: T
        get() {
            val v1 = _value
            if (v1 !== UninitializedValue) {
                @Suppress("UNCHECKED_CAST")
                return v1 as T
            }

            return synchronized(lock) {
                val v2 = _value
                if (v2 !== UninitializedValue) {
                    @Suppress("UNCHECKED_CAST")
                    (v2 as T)
                } else {
                    val typedValue = initializer!!()
                    _value = typedValue
                    initializer = null
                    typedValue
                }
            }
        }

    override fun isInitialized(): Boolean = _value !== UninitializedValue

    override fun toString(): String = if (isInitialized()) value.toString() else "Lazy value not initialized yet."

    @Suppress("UnusedPrivateMember")
    private fun writeReplace(): Any = InitializedLazyImpl(value)
}
