package com.example.comparegetimagespeed

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET(URL.REQUEST_URL)
    fun getImages(): Call<UrlDto>
}