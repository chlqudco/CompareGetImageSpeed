package com.example.comparegetimagespeed.api

import com.example.comparegetimagespeed.URL.REQUEST_URL
import com.example.comparegetimagespeed.response.UrlDto
import com.example.comparegetimagespeed.response.UrlResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(REQUEST_URL)
    fun getImages(): Call<UrlDto>

    @GET(REQUEST_URL)
    suspend fun getImagesWithCoroutine(): Response<UrlDto>

    @GET("/api/deer-images")
    suspend fun getImagesPaging(
        @Query("page") page:Int
    ): Response<UrlResponse>
}