package com.example.core.di.network

import com.example.core.di.model.ApiResponse
import com.example.core.di.model.SubjectResponse
import retrofit2.Response
import retrofit2.http.GET

interface ULessonService {

    @GET("content/grade")
    suspend fun getSubjectsAndDetails(): Response<ApiResponse<SubjectResponse>>
}