package com.jeongbj.glim.common.response

data class BaseResponse<T>(
    val status: Int,
    val message: String?,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T, message: String? = null): BaseResponse<T> =
            BaseResponse(status = 200, message = message, data = data)

        fun error(status: Int, message: String): BaseResponse<Unit> =
            BaseResponse(status = status, message = message, data = null)
    }
}

