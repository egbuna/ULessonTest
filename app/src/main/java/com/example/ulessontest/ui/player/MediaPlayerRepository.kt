package com.example.ulessontest.ui.player

import com.example.core.data.ULessonDataManager
import com.example.core.room.entities.RecentlyWatched
import com.example.core.util.DefaultDispatcherProvider
import com.example.ulessontest.utils.BaseRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MediaPlayerRepository
@Inject constructor(private val dataManager: ULessonDataManager, dispatcherProvider: DefaultDispatcherProvider): BaseRepository(dispatcherProvider) {

    suspend fun saveRecentlyWatchedVideo(recentlyWatched: RecentlyWatched) {
        withContext(dispatcherProvider.io()) {dataManager.saveRecentVideo(recentlyWatched)}
    }
}