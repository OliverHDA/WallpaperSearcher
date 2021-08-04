package ru.oliverhd.wallpapersearcher.model

import com.google.gson.annotations.SerializedName

data class ResponseImage(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("previewURL") val previewURL: String,
    @field:SerializedName("largeImageURL") val largeImageURL: String
)