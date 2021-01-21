package com.example.core.network

import com.example.core.model.ApiResponse
import com.example.core.model.SubjectResponse
import retrofit2.Response
import retrofit2.http.GET

interface ULessonService {

    @GET("content/grade")
    suspend fun getSubjectsAndDetails(): Response<ApiResponse<SubjectResponse>>
}