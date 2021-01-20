package com.example.core.di.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.di.model.SubjectAndLesson
import com.example.core.di.room.dao.RecentlyWatchedDao
import com.example.core.di.room.dao.SubjectsDataDao
import com.example.core.di.room.entities.RecentlyViewed
import com.example.core.di.room.entities.RecentlyWatched

@Database(entities = [RecentlyViewed::class, RecentlyWatched::class, SubjectAndLesson::class],version = 2)
@TypeConverters(Converters::class)
abstract class ULessonDatabase : RoomDatabase() {

    abstract fun recentlyWatchedDao(): RecentlyWatchedDao
    abstract fun subjectDataDao(): SubjectsDataDao

    companion object {

        fun getInstance(context: Context): ULessonDatabase = buildDatabase(context)

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, ULessonDatabase::class.java, "ulesson")
            .fallbackToDestructiveMigration()
            .build()
    }
}