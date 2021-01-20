package com.example.ulessontest.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.core.room.ULessonDatabase
import com.example.core.room.dao.RecentlyWatchedDao
import com.example.core.room.entities.RecentlyWatched
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RecentlyWatchedDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ULessonDatabase
    private lateinit var dao: RecentlyWatchedDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ULessonDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.recentlyWatchedDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertRecentlyWatched() = runBlockingTest {
        val recentlyWatchedItem = RecentlyWatched("Make this money", "How to make money", "google.com", "Money", "01/12/2021", "mmm.com")
        dao.saveWatched(recentlyWatchedItem)
        val recentlyWatchedItems = dao.getRecentlyWatched()
        assertThat(recentlyWatchedItems).contains(recentlyWatchedItem)
    }

    @Test
    fun fetchRecentlyWatchedItems() = runBlockingTest {
        val recentlyWatchedItem1 = RecentlyWatched("Make this money1", "How to make money1", "google.com", "Money1", "01/12/2021", "mmm.com")
        val recentlyWatchedItem2 = RecentlyWatched("Make this money2", "How to make money2", "google.com", "Money2", "01/12/2021", "mmm.com")
        val recentlyWatchedItem3 = RecentlyWatched("Make this money3", "How to make money3", "google.com", "Money3", "01/12/2021", "mmm.com")
        dao.saveWatched(recentlyWatchedItem1)
        dao.saveWatched(recentlyWatchedItem2)
        dao.saveWatched(recentlyWatchedItem3)
        val recentlyWatchedItems = dao.getRecentlyWatched()
        assertThat(recentlyWatchedItems).hasSize(3)
    }

}