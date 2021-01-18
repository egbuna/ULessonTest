package com.example.core.di.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class SubjectResponse(
        @SerializedName("status")
        var status: String,
        var message: String,
        @SerializedName("subjects")
        val subjects: List<SubjectAndLesson>
)
@Keep
@Parcelize
data class SubjectAndLesson(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("chapters")
        val chapters: List<Chapter>
) : Parcelable

@Parcelize
data class Chapter(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("lessons")
        val lessons: List<Lesson>
) : Parcelable

@Parcelize
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
) : Parcelable