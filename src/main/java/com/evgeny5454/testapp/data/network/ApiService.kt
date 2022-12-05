package com.evgeny5454.testapp.data.network

import com.evgeny5454.testapp.data.model.Characters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("public/characters")
    suspend fun getCharacters(
        @Query("ts") timeStamp: String,
        @Query("apikey") apikey: String,
        @Query("hash") hashMD5: String,
    ): Response<Characters>
}