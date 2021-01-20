package com.example.ulessontest.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import javax.inject.Inject

class DashBoardViewModel
    @ViewModelInject constructor(private val dashBoardRepository: DashBoardRepository): ViewModel() {

    private val _getSubjectMutableLiveData = MutableLiveData<Unit>()
    val getSubjectLiveData = _getSubjectMutableLiveData.switchMap {
        liveData {
            emitSource(dashBoardRepository.getSubjectsData())
        }
    }

    init {
        _getSubjectMutableLiveData.value = Unit
    }

    suspend fun getRecentlyWatchedVideos() = dashBoardRepository.fetchRecentlyWatchedVideo()

}