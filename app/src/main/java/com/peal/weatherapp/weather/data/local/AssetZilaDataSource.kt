package com.peal.weatherapp.weather.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.peal.weatherapp.weather.data.local.dto.ZilaDto
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject


class AssetZilaDataSource @Inject constructor(
    private val context: Context
) : ZilaDataSource {
    override suspend fun getZilaList(): List<ZilaDto> {
        val json = try {
            context.assets.open("ZilaList.json").bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            throw FileNotFoundException("ZilaList.json not found in assets")
        }

        return try {
            val type = object : TypeToken<List<ZilaDto>>() {}.type
            Gson().fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            throw JsonParseException("Error parsing ZilaList.json")
        }
    }
}
