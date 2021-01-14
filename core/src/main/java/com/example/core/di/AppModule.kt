package com.example.core.di

import android.content.Context
import com.example.core.di.network.UlessonService
import com.example.core.di.room.ULessonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideService(): UlessonService {
        return Retrofit.Builder()
            .baseUrl("https://jackiechanbruteforce.ulesson.com/3p/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UlessonService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocalDb(
        @ApplicationContext context: Context): ULessonDatabase {
        return ULessonDatabase.getInstance(context)
    }

}