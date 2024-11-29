package com.peal.weatherapp.core.data.network


import com.peal.weatherapp.core.domain.util.NetworkError
import com.peal.weatherapp.core.domain.util.Result
import retrofit2.Response

inline fun <reified T> responseToResult(
    response: Response<T>
): Result<T, NetworkError> {
    return when {
        response.isSuccessful -> {
            val body = response.body()
            if (body != null) {
                Result.Success(body)
            } else {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }
        response.code() == 408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        response.code() == 429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        response.code() in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}