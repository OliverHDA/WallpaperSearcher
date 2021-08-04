package ru.oliverhd.wallpapersearcher.ui.categorylist

import ru.oliverhd.wallpapersearcher.model.CategoryResponseData

sealed class AppData {
    data class Success(val categoryResponseData: CategoryResponseData) : AppData()
    data class Error(val error: Throwable) : AppData()
    data class Loading(val progress: Int?) : AppData()
}