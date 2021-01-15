package com.example.ulessontest.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.core.di.data.ULessonDataManager
import com.example.core.di.model.SubjectAndLesson
import com.example.core.di.model.SubjectResponse
import com.example.core.di.network.NetworkStatus
import com.example.core.di.util.DispatcherProvider
import com.example.ulessontest.utils.BaseRepository
import javax.inject.Inject

class DashBoardRepository
    @Inject constructor(dispatcherProvider: DispatcherProvider, private val uLessonDataManager: ULessonDataManager) : BaseRepository(dispatcherProvider) {

    suspend fun getSubjects(): LiveData<NetworkStatus<SubjectResponse>> {
        return processResponseAsLiveData {
            uLessonDataManager.getSubjectsAndDetails()
        }
    }
}