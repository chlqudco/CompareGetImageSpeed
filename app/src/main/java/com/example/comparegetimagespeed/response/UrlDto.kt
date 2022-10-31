package com.example.comparegetimagespeed.response

import com.example.comparegetimagespeed.model.UrlModel
import com.google.gson.annotations.SerializedName

data class UrlDto(
    @SerializedName("images") val images: List<UrlModel>
) {
}