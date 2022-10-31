package com.example.comparegetimagespeed.api

import com.example.comparegetimagespeed.URL
import com.example.comparegetimagespeed.response.UrlDto
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET(URL.REQUEST_URL)
    fun getImages(): Call<UrlDto>
}