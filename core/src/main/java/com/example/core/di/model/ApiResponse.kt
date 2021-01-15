package com.example.core.di.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiResponse<T>(
    var data: T
)