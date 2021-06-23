package com.baloot.data.model

import com.google.gson.annotations.SerializedName

data class ArticleApiResult(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)