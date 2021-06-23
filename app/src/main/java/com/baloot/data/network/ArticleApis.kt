package com.baloot.data.network

import com.baloot.API_KEY
import com.baloot.data.model.ArticleApiResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ArticleApis {
    @GET("everything")
    suspend fun getArticle(
        @Query("page") page: Int,
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<ArticleApiResult>
}