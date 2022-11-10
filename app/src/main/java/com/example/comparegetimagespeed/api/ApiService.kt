package com.example.comparegetimagespeed.api

import com.example.comparegetimagespeed.URL
import com.example.comparegetimagespeed.URL.REQUEST_URL
import com.example.comparegetimagespeed.response.UrlDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(REQUEST_URL)
    fun getImages(): Call<UrlDto>

    @GET(REQUEST_URL)
    suspend fun getImagesWithCoroutine(): Response<UrlDto>
}