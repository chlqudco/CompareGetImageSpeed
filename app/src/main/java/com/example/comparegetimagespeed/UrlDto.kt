package com.example.comparegetimagespeed

import com.google.gson.annotations.SerializedName

data class UrlDto(
    @SerializedName("images") val images: List<UrlModel>
) {
}