package com.jeongbj.core.common

sealed class ApiException(
    override val message: String
) : RuntimeException(message) {

    data class HttpError(
        val code: Int,
        val errorMessage: String
    ) : ApiException(errorMessage)

    class NetworkError : ApiException("Network error")
    class UnknownError : ApiException("Unknown error")
}