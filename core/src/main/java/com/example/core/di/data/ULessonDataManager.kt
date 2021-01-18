package com.example.core.di.data

import com.example.core.di.model.ApiResponse
import com.example.core.di.model.SubjectAndLesson
import com.example.core.di.model.SubjectResponse
import com.example.core.di.network.ULessonService
import retrofit2.Response
import javax.inject.Inject

class ULessonDataManager
    @Inject constructor(val uLessonService: ULessonService): DataManager {
    override suspend fun getSubjectsAndDetails(): Response<ApiResponse<SubjectResponse>> = uLessonService.getSubjectsAndDetails()

}