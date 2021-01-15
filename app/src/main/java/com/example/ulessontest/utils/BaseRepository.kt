package com.example.ulessontest.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.core.di.model.ApiResponse
import com.example.core.di.model.SubjectResponse
import com.example.core.di.network.NetworkStatus
import com.example.core.di.util.AppUtil
import com.example.core.di.util.DispatcherProvider
import com.example.core.di.util.NetworkConstant
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

abstract class BaseRepository(protected val dispatcherProvider: DispatcherProvider) {

    protected suspend inline fun <reified ResponseType> processResponseAsLiveData(
        crossinline block: suspend () -> Response<ApiResponse<ResponseType>>
    ): LiveData<NetworkStatus<ResponseType>> {

        return liveData<NetworkStatus<ResponseType>>(dispatcherProvider.io()) {
            emit(NetworkStatus.Loading())
            processResponseInternal(block) {
                emit(it)
            }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend inline fun <reified ResponseType, T> processResponseInternal(
        crossinline block: suspend () -> Response<ApiResponse<ResponseType>>,
        returnResponse: (response: NetworkStatus<ResponseType>) -> T
    ): T {
        try {
            val response = block()
            Log.e("STATUS", "status ${response.body()}")
            val responseMessage = response.body()?.data as SubjectResponse
            return if (response.isSuccessful && responseMessage.status == "success") {
                returnResponse(
                    NetworkStatus.Success(
                        response.body()?.data,
                        response.body()
                    )
                )
            } else if (response.isSuccessful.not()) {
                throw HttpException(response)
            } else {
                val responseMessage = response.body()?.data as SubjectResponse
                returnResponse(
                    NetworkStatus.Error(
                        responseMessage.message,
                        data = response.body()
                    )
                )
            }
        } catch (e: Exception) {
            return when (e) {
                is ConnectException -> {
                    returnResponse(
                        NetworkStatus.Error(
                            NetworkConstant.CONNECT_EXCEPTION
                        )
                    )
                }
                is UnknownHostException -> {
                    returnResponse(
                        NetworkStatus.Error(
                            NetworkConstant.UNKNOWN_HOST_EXCEPTION
                        )
                    )
                }
                is SocketTimeoutException -> {
                    returnResponse(
                        NetworkStatus.Error(
                            NetworkConstant.UNKNOWN_HOST_EXCEPTION
                        )
                    )
                }
                is HttpException -> {
                    try {
                        val type = object : TypeToken<ApiResponse<ResponseType>>() {}.type
                        val response = AppUtil.gson.fromJson<ApiResponse<ResponseType>>(e.response()?.errorBody()?.string()!!, type)
                        val errorMessage = response.data as SubjectResponse

                        returnResponse(
                            NetworkStatus.Error(
                                errorMessage.message,
                                data = response
                            )
                        )
                    } catch (err: JSONException) {
                        Log.e("JSON Exception", err.localizedMessage!!)
                        returnResponse(
                            NetworkStatus.Error(
                                NetworkConstant.UNKNOWN_NETWORK_EXCEPTION
                            )
                        )
                    } catch (err: IOException) {
                        Log.e("Network IO Exception", err.localizedMessage!!)
                        returnResponse(
                            NetworkStatus.Error(
                                NetworkConstant.UNKNOWN_NETWORK_EXCEPTION
                            )
                        )
                    } catch (err: SSLException) {
                        Log.e("Network IO Exception", err.localizedMessage!!)
                        returnResponse(
                            NetworkStatus.Error(
                                NetworkConstant.SSL_EXCEPTION
                            )
                        )
                    } catch (err: JsonSyntaxException) {
                        Log.e("Json Syntax Exception", err.localizedMessage!!)
                        returnResponse(
                            NetworkStatus.Error(
                                NetworkConstant.JSON_SYNTAX_EXCEPTION
                            )
                        )
                    }
                }
                is SSLException -> {
                    Log.e("Network IO Exception", e.localizedMessage!!)
                    returnResponse(
                        NetworkStatus.Error(
                            NetworkConstant.SSL_EXCEPTION
                        )
                    )
                }
                else -> {
                    Log.e( "UNKNOWN_NETWORK",e.message.orEmpty())
                    returnResponse(
                        NetworkStatus.Error(
                            NetworkConstant.UNKNOWN_NETWORK_EXCEPTION
                        )
                    )
                }
            }
        }
    }
}