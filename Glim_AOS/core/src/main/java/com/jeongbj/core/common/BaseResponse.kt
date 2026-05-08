package com.jeongbj.core.common

data class BaseResponse<out T> (
    val message : String,
    val status: Int,
    val data: T
)