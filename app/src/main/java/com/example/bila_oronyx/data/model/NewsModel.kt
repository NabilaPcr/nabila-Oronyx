package com.example.bila_oronyx.data.model

import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String?,

    @SerializedName("urlToImage")
    val urlToImage: String?,

    @SerializedName("publishedAt")
    val publishedAt: String?,

    @SerializedName("author")
    val author: String?,

    @SerializedName("content")
    val content: String?
)

data class NewsResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    val totalResults: Int,

    @SerializedName("articles")
    val articles: List<NewsModel>
)