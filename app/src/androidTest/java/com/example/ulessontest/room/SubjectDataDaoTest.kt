package com.example.ulessontest.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.core.model.Chapter
import com.example.core.model.Lesson
import com.example.core.model.SubjectAndLesson
import com.example.core.room.ULessonDatabase
import com.example.core.room.dao.SubjectsDataDao
import com.example.ulessontest.util.getOrAwaitValue
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
class SubjectDataDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ULessonDatabase
    private lateinit var dao: SubjectsDataDao

    @Before
    fun setUp() {
         database = Room.inMemoryDatabaseBuilder(
             ApplicationProvider.getApplicationContext(),
             ULessonDatabase::class.java
         ).allowMainThreadQueries().build()
        dao = database.subjectDataDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertSubjectItem() = runBlockingTest {
        dao.saveSubjectsData(listOf(buildSubjectItem()))
        val allSubjectAndLesson = dao.getSubjectDataAsLiveData().getOrAwaitValue()
        assertThat(allSubjectAndLesson).contains(buildSubjectItem())
    }

    private fun buildSubjectItem(): SubjectAndLesson {
        val lesson = Lesson("1234", "How to make money", "1234.jpeg", "2334jdfj.mp4", 1,2)
        val chapter = Chapter("123", "Ray", listOf(lesson))
        return SubjectAndLesson("1234", "Make money", "inenr", listOf(chapter))
    }
}