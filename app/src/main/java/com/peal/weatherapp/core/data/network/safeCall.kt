package com.peal.weatherapp.core.data.network

import com.peal.weatherapp.core.domain.util.NetworkError
import com.peal.weatherapp.core.domain.util.Result
import kotlinx.coroutines.ensureActive
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    apiCall: () -> Response<T>
): Result<T, NetworkError> {
    val response = try {
        apiCall()
    } catch (e: IOException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}

