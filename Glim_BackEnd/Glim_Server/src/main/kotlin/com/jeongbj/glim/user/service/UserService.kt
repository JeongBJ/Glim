package com.jeongbj.glim.user.service

import com.jeongbj.glim.common.exception.UserNotFoundException
import com.jeongbj.glim.infra.bucket.BucketService
import com.jeongbj.glim.user.dto.request.ProfileRequest
import com.jeongbj.glim.user.dto.response.UserResponse
import com.jeongbj.glim.user.mapper.toResponse
import com.jeongbj.glim.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val bucketService: BucketService
) {
    fun updateProfile(userSeq: Long, profileRequest: ProfileRequest, multipartFile: MultipartFile?): UserResponse {
        val user = userRepository.findById(userSeq).orElseThrow { UserNotFoundException() }
        val oldImageUrl = user.imageUrl

        val imageUrl = multipartFile?.let {
            bucketService.uploadImage(it,BucketService.PROFILE)
        } ?: oldImageUrl

        user.updateProfile(profileRequest.nickname, imageUrl)

        if (multipartFile != null && oldImageUrl != null) {
            bucketService.deleteImage(oldImageUrl)
        }
        return user.toResponse()
    }
}