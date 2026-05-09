package com.jeongbj.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <T> flowResult(
    crossinline block: suspend () -> T
): Flow<ResultType<T>> = flow {
    emit(ResultType.Loading)
    try {
        emit(ResultType.Success(block()))
    } catch (e: Exception) {
        emit(ResultType.Error(e))
    }
}

fun <T> BaseResponse<T>.unwrap(): T {
    if (status != 200) {
        throw ApiException.HttpError(status, message ?: "Unknown error")
    }
    return data ?: throw ApiException.HttpError(status, message ?: "Unknown error")
}

fun BaseResponse<Unit>.unwrap(): Unit {
    if (status != 200) {
        throw ApiException.HttpError(status, message ?: "Unknown error")
    }
}