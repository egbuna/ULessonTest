package com.example.core.di

import android.content.Context
import com.example.core.BuildConfig
import com.example.core.di.data.DataManager
import com.example.core.di.data.ULessonDataManager
import com.example.core.di.network.ULessonService
import com.example.core.di.room.ULessonDatabase
import com.example.core.di.util.DefaultDispatcherProvider
import com.example.core.di.util.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideService(): ULessonService {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val okHttpCBuilder = OkHttpClient.Builder()

        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        okHttpCBuilder.readTimeout(1, TimeUnit.MINUTES)
        okHttpCBuilder.connectTimeout(1, TimeUnit.MINUTES)
        okHttpCBuilder.retryOnConnectionFailure(false)
        okHttpCBuilder.addNetworkInterceptor(httpLoggingInterceptor)

        val client: OkHttpClient = okHttpCBuilder.build()

        return Retrofit.Builder()
            .baseUrl("https://jackiechanbruteforce.ulesson.com/3p/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ULessonService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocalDb(
        @ApplicationContext context: Context): ULessonDatabase {
        return ULessonDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideULessonDataManager(uLessonDataManager: DataManager) = uLessonDataManager

}

@Module
@InstallIn(ApplicationComponent::class)
abstract class BindingModule {

    @Singleton
    @Binds
    abstract fun bindCoroutineDispatcher(
            dispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider
}