package com.example.ulessontest.ui.player

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.core.room.entities.RecentlyWatched

class PlayMediaViewModel @ViewModelInject constructor(private val mediaPlayerRepository: MediaPlayerRepository) : ViewModel() {

    suspend fun saveRecentlyWatchedVideo(recentlyWatched: RecentlyWatched) = mediaPlayerRepository.saveRecentlyWatchedVideo(recentlyWatched)
}