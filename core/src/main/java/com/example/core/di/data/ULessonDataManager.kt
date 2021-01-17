package com.example.core.di.data

import com.example.core.di.model.ApiResponse
import com.example.core.di.model.SubjectAndLesson
import com.example.core.di.model.SubjectResponse
import com.example.core.di.network.ULessonService
import com.example.core.di.room.dao.RecentlyWatchedDao
import com.example.core.di.room.entities.RecentlyWatched
import retrofit2.Response
import javax.inject.Inject

class ULessonDataManager
    @Inject constructor(private val uLessonService: ULessonService, private val recentlyWatchedDao: RecentlyWatchedDao): DataManager {
    override suspend fun getSubjectsAndDetails(): Response<ApiResponse<SubjectResponse>> = uLessonService.getSubjectsAndDetails()
    override suspend fun getRecentlyWatchedVideos(): List<RecentlyWatched> = recentlyWatchedDao.getRecentlyWatched()
    override suspend fun saveRecentVideo(recentlyWatched: RecentlyWatched): Long = recentlyWatchedDao.saveWatched(recentlyWatched)

}