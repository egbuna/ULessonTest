package com.example.core.data

import androidx.lifecycle.LiveData
import com.example.core.model.ApiResponse
import com.example.core.model.SubjectAndLesson
import com.example.core.model.SubjectResponse
import com.example.core.room.entities.RecentlyWatched
import retrofit2.Response

interface DataManager {
    suspend fun getSubjectsAndDetails(): Response<ApiResponse<SubjectResponse>>
    suspend fun getRecentlyWatchedVideos():List<RecentlyWatched>
    suspend fun saveRecentVideo(recentlyWatched: RecentlyWatched): Long
    suspend fun saveSubjectData(data: List<SubjectAndLesson>) : List<Long>
    suspend fun getSubjectData(): List<SubjectAndLesson>
    fun getSubjectDataAsLiveData(): LiveData<List<SubjectAndLesson>>
}