package com.jeongbj.core

data class BaseResponse<out T> (
    val message : String,
    val status: Int,
    val data: T
)