package ru.oliverhd.wallpapersearcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oliverhd.wallpapersearcher.BuildConfig
import ru.oliverhd.wallpapersearcher.model.CategoryResponseData
import ru.oliverhd.wallpapersearcher.retrofit.RetrofitImpl
import ru.oliverhd.wallpapersearcher.ui.categorylist.AppData
import ru.oliverhd.wallpapersearcher.utils.IMAGE_LIST_SIZE
import ru.oliverhd.wallpapersearcher.utils.IMAGE_TYPE_REQUEST
import java.util.*

class CategoryViewModel(
    private val liveData: MutableLiveData<AppData> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {

    fun getLiveData() = liveData

    fun getPictureOfTheDayByDate(category: String): LiveData<AppData> {
        sendServerRequest(category)
        return liveData
    }

    private fun sendServerRequest(category: String) {
        liveData.value = AppData.Loading(null)
        val apiKey: String = BuildConfig.PIXABAY_API_KEY
        if (apiKey.isBlank()) {
            AppData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl()
                .getImageByCategory(
                    apiKey,
                    IMAGE_TYPE_REQUEST,
                    category.lowercase(Locale.getDefault()),
                    IMAGE_LIST_SIZE
                )
                .enqueue(object :
                    Callback<CategoryResponseData> {
                    override fun onResponse(
                        call: Call<CategoryResponseData>,
                        response: Response<CategoryResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveData.value =
                                AppData.Success(response.body()!!)
                        } else {
                            val message = response.errorBody()?.string()
                            if (message.isNullOrEmpty()) {
                                liveData.value =
                                    AppData.Error(Throwable("Unidentified error"))
                            } else {
                                liveData.value =
                                    AppData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<CategoryResponseData>, t: Throwable) {
                        liveData.value = AppData.Error(t)
                    }
                })
        }
    }
}