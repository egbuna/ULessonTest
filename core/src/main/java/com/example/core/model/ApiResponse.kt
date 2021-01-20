package com.example.core.model

import androidx.annotation.Keep

@Keep
data class ApiResponse<T>(
    var data: T
)