package com.example.core.di.room.entities

import androidx.room.Entity

@Entity(primaryKeys = ["chapter", "lesson", "subject"], tableName = "recently_watched")
data class RecentlyWatched(
    val chapter: String,
    val lesson: String,
    val mediaUrl: String,
    val subject: String,
    val date: String,
    val icon: String
)