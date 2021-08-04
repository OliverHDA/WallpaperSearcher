package ru.oliverhd.wallpapersearcher.model

import com.google.gson.annotations.SerializedName

data class CategoryResponseData(
    @field:SerializedName("total") val total: Int,
    @field:SerializedName("totalHits") val totalHits: Int,
    @field:SerializedName("hits") val images: List<ResponseImage>
)