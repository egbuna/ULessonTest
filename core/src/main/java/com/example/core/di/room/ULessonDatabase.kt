package com.example.core.di.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.core.di.room.entities.RecentlyViewed

@Database(entities = [RecentlyViewed::class],version = 1)
abstract class ULessonDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: ULessonDatabase? = null

        fun getInstance(context: Context): ULessonDatabase = buildDatabase(context)

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, ULessonDatabase::class.java, "ulesson")
            .build()
    }
}