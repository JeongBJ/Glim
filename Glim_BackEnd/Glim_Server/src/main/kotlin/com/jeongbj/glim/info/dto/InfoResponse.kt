package com.jeongbj.glim.info.dto

import com.jeongbj.glim.user.dto.response.UserResponse

data class InfoResponse(
    val user: UserResponse,
    val numLikes: Int,
    val numQuotes: Int,
    val contributions: List<GlimContributionResponse>,
)
