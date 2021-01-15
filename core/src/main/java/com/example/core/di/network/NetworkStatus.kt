package com.example.core.di.network

import com.example.core.di.model.ApiResponse

sealed class NetworkStatus<T>(data: T? = null, message: String? = null, apiResponse: ApiResponse<T>? = null) {
    data class Success<T>(val data: T?, val apiResponse: ApiResponse<T>? = null) : NetworkStatus<T>(data, apiResponse = apiResponse)
    data class Loading<T>(val data: T? = null) : NetworkStatus<T>(data)
    data class Error<T>(val message: String, val data: ApiResponse<T>? = null) : NetworkStatus<T>(message = message, apiResponse = data)
}