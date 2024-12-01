package com.peal.weatherapp.weather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SuggestionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuggestion(suggestion: SuggestionEntity)

    @Query("SELECT DISTINCT `query` FROM suggestions WHERE `query` LIKE :query || '%' LIMIT 5")
    suspend fun getSuggestions(query: String): List<String>
}