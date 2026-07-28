package com.jeongbj.glim.common.dto

data class CursorRequest(
    val cursor: Long? = null,
    val size: Int = 20
)