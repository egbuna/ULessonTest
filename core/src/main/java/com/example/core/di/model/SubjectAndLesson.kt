package com.example.core.di.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class SubjectResponse(
        @SerializedName("status")
        var status: String,
        var message: String,
        @SerializedName("subjects")
        val subjects: List<SubjectAndLesson>
)
@Keep
data class SubjectAndLesson(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("chapters")
        val chapters: List<Chapter>
)

data class Chapter(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("lessons")
        val lessons: List<Lesson>
)

data class Lesson(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("media_url")
        val mediaUrl: String,
        @SerializedName("subject_id")
        val subjectId: Int,
        @SerializedName("chapter_id")
        val chapterId: Int
)