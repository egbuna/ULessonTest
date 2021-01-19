package com.example.core.di.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
@Entity(tableName = "subject")
data class SubjectAndLesson(
        @ColumnInfo(name = "id")
        @SerializedName("id")
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "name")
        @SerializedName("name")
        val name: String,
        @SerializedName("icon")
        @ColumnInfo(name = "icon")
        val icon: String,
        @ColumnInfo(name = "chapters")
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

        @ColumnInfo(name = "subject_id")
        @SerializedName("subject_id")
        val subjectId: Int,

        @ColumnInfo(name = "chapter_id")
        @SerializedName("chapter_id")
        val chapterId: Int
) : Parcelable