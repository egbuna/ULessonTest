package com.example.core.di.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.di.room.entities.RecentlyWatched

@Dao
interface RecentlyWatchedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWatched(recentlyWatched: RecentlyWatched): Long
    @Query("select * from recently_watched order by date desc")
    suspend fun getRecentlyWatched(): List<RecentlyWatched>
}