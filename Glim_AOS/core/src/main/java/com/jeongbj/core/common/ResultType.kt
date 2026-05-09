package com.jeongbj.core.common

sealed class ResultType<out T> {
    data object Loading : ResultType<Nothing>()

    data class Success<T>(val data: T) : ResultType<T>()

    data class Error(val exception: Throwable) : ResultType<Nothing>()

}