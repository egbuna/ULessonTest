package com.example.core.di.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.core.di.room.dao.RecentlyWatchedDao
import com.example.core.di.room.entities.RecentlyViewed
import com.example.core.di.room.entities.RecentlyWatched

@Database(entities = [RecentlyViewed::class, RecentlyWatched::class],version = 1)
abstract class ULessonDatabase : RoomDatabase() {

    abstract fun recentlyWatchedDao(): RecentlyWatchedDao

    companion object {

        fun getInstance(context: Context): ULessonDatabase = buildDatabase(context)

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, ULessonDatabase::class.java, "ulesson")
            .build()
    }
}