package com.jeongbj.core

import java.io.IOException

sealed class ResultType<out T> {
    data object Loading : ResultType<Nothing>()

    data class Success<T>(val data: T) : ResultType<T>()

    data class Error(val exception: Throwable) : ResultType<Nothing>() {
        val isNetworkError = exception is IOException
    }
}