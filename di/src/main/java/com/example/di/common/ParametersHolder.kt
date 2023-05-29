package com.example.di.common

@Suppress("UNCHECKED_CAST")
class ParametersHolder(
    private val values: List<DiParameter> = emptyList(),
) {

    inline fun <reified T : Any> get(qualifier: String = ""): T {
        return getOrNull(T::class.java, qualifier)
            ?: error("No value found for type ${T::class.java}")
    }

    fun <T> getOrNull(cls: Class<*>, qualifier: String): T? {
        return values.firstNotNullOfOrNull { value ->
            if (cls.isInstance(value.entry) && value.qualifier == qualifier) value.entry as? T else null
        }
    }
}