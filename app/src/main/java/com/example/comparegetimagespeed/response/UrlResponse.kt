package com.example.comparegetimagespeed.response

import com.google.gson.annotations.SerializedName

data class UrlResponse(
    @SerializedName("images") val images: List<String>,
    @SerializedName("is_end") val is_end : Boolean,
    @SerializedName("is_start") val is_start : Boolean
)