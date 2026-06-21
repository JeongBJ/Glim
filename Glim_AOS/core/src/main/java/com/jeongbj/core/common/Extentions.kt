package com.jeongbj.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate

inline fun <T> flowResult(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline block: suspend () -> T
): Flow<ResultType<T>> = flow {
    emit(ResultType.Loading)
    try {
        emit(ResultType.Success(block()))
    } catch (e: Exception) {
        emit(ResultType.Error(e))
    }
}.flowOn(dispatcher)

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

fun String?.toLocalDate(): LocalDate? {
    return this
        ?.takeIf { it.isNotBlank() }
        ?.let {
            runCatching { LocalDate.parse(it) }
                .getOrNull()
        }
}

fun ByteArray.toMultipartImage(): MultipartImage = MultipartImage(
    bytes = this,
    fileName = "image.jpg",
    mimeType = "image/jpeg",
)