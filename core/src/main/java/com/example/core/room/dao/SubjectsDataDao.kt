package com.example.core.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.model.SubjectAndLesson

@Dao
interface SubjectsDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSubjectsData(data: List<SubjectAndLesson>): List<Long>

    @Query("select * from subject")
    suspend fun getSubjectData(): List<SubjectAndLesson>

    @Query("select * from subject")
    fun getSubjectDataAsLiveData(): LiveData<List<SubjectAndLesson>>
}