package com.peal.weatherapp.weather.domain.usecase

import com.google.gson.JsonParseException
import com.peal.weatherapp.core.domain.util.DomainError
import com.peal.weatherapp.core.domain.util.Result
import com.peal.weatherapp.weather.data.local.ZilaDataSource
import com.peal.weatherapp.weather.data.mappers.toZila
import com.peal.weatherapp.weather.domain.models.Zila
import java.io.FileNotFoundException
import javax.inject.Inject


class ZilaListUseCase @Inject constructor(
    private val zilaDataSource: ZilaDataSource
) {
    suspend fun getZilaList(): Result<List<Zila>, DomainError> {
        return try {
            val zilaDtos = zilaDataSource.getZilaList()
            if (zilaDtos.isEmpty()) {
                Result.Failure(DomainError.DATA_NOT_FOUND)
            } else {
                Result.Success(zilaDtos.map { it.toZila() })
            }
        } catch (e: FileNotFoundException) {
            Result.Failure(DomainError.FILE_NOT_FOUND)
        } catch (e: JsonParseException) {
            Result.Failure(DomainError.PARSING_ERROR)
        } catch (e: Exception) {
            Result.Failure(DomainError.UNKNOWN_ERROR)
        }
    }
}