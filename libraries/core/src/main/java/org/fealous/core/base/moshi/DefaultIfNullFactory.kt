package org.fealous.core.base.moshi

import com.squareup.moshi.*
import java.lang.reflect.Type

class DefaultIfNullFactory : JsonAdapter.Factory {
    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? {
        val delegate = moshi.nextAdapter<Any>(this, type, annotations)
        if (annotations.isNotEmpty()) return null
        if (type === Boolean::class.javaPrimitiveType) return delegate
        if (type === Byte::class.javaPrimitiveType) return delegate
        if (type === Char::class.javaPrimitiveType) return delegate
        if (type === Double::class.javaPrimitiveType) return delegate
        if (type === Float::class.javaPrimitiveType) return delegate
        if (type === Int::class.javaPrimitiveType) return delegate
        if (type === Long::class.javaPrimitiveType) return delegate
        if (type === Short::class.javaPrimitiveType) return delegate
        if (type === Boolean::class.java) return delegate
        if (type === Byte::class.java) return delegate
        if (type === Char::class.java) return delegate
        if (type === Double::class.java) return delegate
        if (type === Float::class.java) return delegate
        if (type === Int::class.java) return delegate
        if (type === Long::class.java) return delegate
        if (type === Short::class.java) return delegate
        if (type === String::class.java) return delegate
        return object : JsonAdapter<Any>() {
            override fun fromJson(reader: JsonReader): Any? {
                return when {
                    reader.peek() == JsonReader.Token.BEGIN_OBJECT -> {
                        delegate.fromJson(JsonReaderSkipNullValuesWrapper(reader))
                    }
                    reader.peek() == JsonReader.Token.BEGIN_ARRAY -> {
                        (delegate.fromJson(reader) as? ArrayList<*>)?.filterNotNull()
                    }
                    else -> {
                        delegate.fromJson(reader)
                    }
                }
            }
            override fun toJson(writer: JsonWriter, value: Any?) {
                return delegate.toJson(writer, value)
            }
        }
    }
}