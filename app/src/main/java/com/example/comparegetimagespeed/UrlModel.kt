package com.example.comparegetimagespeed

import com.google.gson.annotations.SerializedName

data class UrlModel(
    @SerializedName("id") val id: Int,
    @SerializedName("imageUrl") val imageUrl : String
)