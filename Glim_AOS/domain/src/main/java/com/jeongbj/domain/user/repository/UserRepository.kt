package com.jeongbj.domain.user.repository

import com.jeongbj.core.common.MultipartImage
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.model.UserInfo

interface UserRepository {
    suspend fun updateProfile(image: MultipartImage?, profile: User): User

    suspend fun getUserInfo(): UserInfo
}