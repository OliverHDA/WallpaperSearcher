package ru.oliverhd.wallpapersearcher.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.oliverhd.wallpapersearcher.model.CategoryResponseData

interface CategoryListApi {
    @GET("api/")
    fun getImageByCategory(
        @Query("key") key: String,
        @Query("image_type") image_type: String,
        @Query("category") category: String,
        @Query("per_page") per_page: Int
    ): Call<CategoryResponseData>
}