package com.example.core.room

import androidx.room.TypeConverter
import com.example.core.model.Chapter
import com.example.core.model.Lesson
import com.example.core.util.AppUtil
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromChapters(chapters: List<Chapter>): String {
        return AppUtil.gson.toJson(chapters)
    }

    @TypeConverter
    fun toChapters(data: String): List<Chapter> {
        val type = object : TypeToken<List<Chapter>>() {}.type
        return AppUtil.gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromLessons(lessons: List<Lesson>): String {
        return AppUtil.gson.toJson(lessons)
    }

    @TypeConverter
    fun toLessons(data: String): List<Lesson> {
        val type = object : TypeToken<List<Lesson>>() {}.type
        return AppUtil.gson.fromJson(data, type)
    }
}