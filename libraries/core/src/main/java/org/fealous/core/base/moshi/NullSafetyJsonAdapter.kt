package org.fealous.core.base.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader

class NullSafetyJsonAdapter {
    @FromJson
    fun fromStringJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }

    @FromJson
    fun fromIntJson(reader: JsonReader): Int {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextInt()
        }
        reader.nextNull<Unit>()
        return 0
    }

    @FromJson
    fun fromDoubleJson(reader: JsonReader): Double {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextDouble()
        }
        reader.nextNull<Unit>()
        return 0.0
    }

    @FromJson
    fun fromBooleanJson(reader: JsonReader): Boolean {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextBoolean()
        }
        reader.nextNull<Unit>()
        return false
    }

    @FromJson
    fun fromLongJson(reader: JsonReader): Long {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextLong()
        }
        reader.nextNull<Unit>()
        return 0L
    }
}