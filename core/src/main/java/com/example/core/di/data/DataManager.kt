package com.example.core.di.data

import com.example.core.di.model.ApiResponse
import com.example.core.di.model.SubjectAndLesson
import com.example.core.di.model.SubjectResponse
import com.example.core.di.network.ULessonService
import retrofit2.Response

interface DataManager {
    suspend fun getSubjectsAndDetails(): Response<ApiResponse<SubjectResponse>>
}