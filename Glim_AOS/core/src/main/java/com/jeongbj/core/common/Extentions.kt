package com.jeongbj.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T, R> Flow<BaseResponse<T>>.mapBaseResponse(
    mapper: (T?) -> R
): Flow<ResultType<R>> {
    return map { response ->
        if (response.status == 200) {
            ResultType.Success(mapper(response.data))
        } else {
            ResultType.Error(
                Throwable("Error ${response.status}: ${response.message}")
            )
        }
    }.catch { e ->
        emit(
            ResultType.Error(e)
        )
    }.onStart {
        emit(ResultType.Loading)
    }
}