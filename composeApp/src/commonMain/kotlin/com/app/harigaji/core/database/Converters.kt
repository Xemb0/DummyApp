package com.app.harigaji.core.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromListToString(list: List<String>?): String? {
        return list?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun fromStringToList(value: String?): List<String>? {
        return value?.let { Json.decodeFromString<List<String>>(it) }
    }

    @TypeConverter
    fun fromMapToString(map: Map<String, String>?): String? {
        return map?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun fromStringToMap(value: String?): Map<String, String>? {
        return value?.let { Json.decodeFromString<Map<String, String>>(it) }
    }

}
