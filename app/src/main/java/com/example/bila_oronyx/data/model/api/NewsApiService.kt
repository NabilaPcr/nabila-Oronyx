package com.example.bila_oronyx.data.api

import com.example.bila_oronyx.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "id",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}