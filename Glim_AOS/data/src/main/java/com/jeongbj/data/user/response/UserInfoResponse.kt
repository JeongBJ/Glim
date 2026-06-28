package com.jeongbj.data.user.response

data class UserInfoResponse(
    val user: UserResponse,
    val numLikes: Int,
    val numQuotes: Int,
    val contributions: List<GlimContributionResponse>
)
