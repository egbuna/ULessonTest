package com.example.ulessontest.ui.dashboard

import androidx.lifecycle.liveData
import com.example.core.data.ULessonDataManager
import com.example.core.model.SubjectAndLesson
import com.example.core.network.NetworkStatus
import com.example.core.room.entities.RecentlyWatched
import com.example.core.util.DispatcherProvider
import com.example.ulessontest.utils.BaseRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DashBoardRepository
    @Inject constructor(dispatcherProvider: DispatcherProvider, private val uLessonDataManager: ULessonDataManager) : BaseRepository(dispatcherProvider) {
    
    suspend fun fetchRecentlyWatchedVideo(): List<RecentlyWatched> {
        return withContext(dispatcherProvider.io()) {return@withContext uLessonDataManager.getRecentlyWatchedVideos()}
    }

    suspend fun getSubjectsData() = liveData<List<SubjectAndLesson>> {
        when(val response = processResponse {uLessonDataManager.getSubjectsAndDetails()}) {
            is NetworkStatus.Success -> {
                val returnedData = withContext(dispatcherProvider.io()) {
                    uLessonDataManager.saveSubjectData(response.data?.subjects!!)
                    return@withContext uLessonDataManager.getSubjectData()
                }

                emit(returnedData)
            }
            else -> {
                val returnedData = withContext(dispatcherProvider.io()) {
                    return@withContext uLessonDataManager.getSubjectData()
                }
                emit(returnedData)
            }
        }

    }
}