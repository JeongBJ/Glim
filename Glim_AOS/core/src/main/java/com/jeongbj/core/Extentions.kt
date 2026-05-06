package com.jeongbj.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T, R> Flow<BaseResponse<T>>.mapBaseResponse(
    mapper: (T) -> R
): Flow<ResultType<R>> = this.map { response ->
    if (response.status == 200) {
        if(response.data == null) {ResultType.Success(mapper(Unit as T))}
        else ResultType.Success(mapper(response.data))
    } else {
        ResultType.Error(Throwable("Error ${response.status}: ${response.message}"))
    }
}.catch { e ->
    emit(ResultType.Error(e))
}.onStart {
    emit(ResultType.Loading)
}