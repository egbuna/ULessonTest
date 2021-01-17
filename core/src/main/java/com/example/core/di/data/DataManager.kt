package com.example.core.di.data

import com.example.core.di.model.ApiResponse
import com.example.core.di.model.SubjectAndLesson
import com.example.core.di.model.SubjectResponse
import com.example.core.di.network.ULessonService
import com.example.core.di.room.entities.RecentlyWatched
import retrofit2.Response

interface DataManager {
    suspend fun getSubjectsAndDetails(): Response<ApiResponse<SubjectResponse>>
    suspend fun getRecentlyWatchedVideos():List<RecentlyWatched>
    suspend fun saveRecentVideo(recentlyWatched: RecentlyWatched): Long
}