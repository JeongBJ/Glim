package com.jeongbj.data.user.mapper

import com.jeongbj.core.common.toLocalDate
import com.jeongbj.data.user.response.GlimContributionResponse
import com.jeongbj.data.user.response.UserInfoResponse
import com.jeongbj.data.user.response.UserResponse
import com.jeongbj.domain.user.model.GlimContribution
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.model.UserInfo

fun UserResponse.toDomain() : User = User(
    userSeq = userSeq,
    nickname = nickname ?: "",
    imageUrl = imageUrl
)

fun UserInfoResponse.toDomain(): UserInfo = UserInfo(
    user = user.toDomain(),
    numLikes = numLikes,
    numQuotes = numQuotes,
    contributions = contributions.map { it.toDomain() }
)

fun GlimContributionResponse.toDomain() = GlimContribution(
    date = date.toLocalDate()!!,
    count = count
)