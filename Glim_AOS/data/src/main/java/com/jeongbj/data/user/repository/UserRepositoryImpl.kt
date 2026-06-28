package com.jeongbj.data.user.repository

import com.jeongbj.core.common.MultipartImage
import com.jeongbj.core.common.unwrap
import com.jeongbj.data.network.mapper.toJsonRequestBody
import com.jeongbj.data.network.mapper.toMultipartBody
import com.jeongbj.data.user.datasource.InfoRemoteDataSource
import com.jeongbj.data.user.datasource.UserRemoteDataSource
import com.jeongbj.data.user.mapper.toDomain
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.model.UserInfo
import com.jeongbj.domain.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val infoRemoteDataSource: InfoRemoteDataSource
) : UserRepository {
    override suspend fun updateProfile(
        image: MultipartImage?,
        profile: User
    ): User {
        return userRemoteDataSource.updateProfile(profile.toJsonRequestBody(), image.toMultipartBody())
            .unwrap()
            .toDomain()
    }

    override suspend fun getUserInfo(): UserInfo {
        return infoRemoteDataSource.getInfo().unwrap().toDomain()
    }
}