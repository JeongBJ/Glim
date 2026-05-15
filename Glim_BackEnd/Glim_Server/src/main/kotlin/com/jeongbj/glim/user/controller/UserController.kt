package com.jeongbj.glim.user.controller

import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.user.dto.request.ProfileRequest
import com.jeongbj.glim.user.dto.response.UserResponse
import com.jeongbj.glim.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @PatchMapping("/profile")
    fun updateProfile(
        @AuthenticationPrincipal userSeq: Long,
        @RequestPart("request") request: ProfileRequest,
        @RequestPart("image", required = false) image: MultipartFile?
    ): ResponseEntity<BaseResponse<UserResponse>> {
        val response = userService.updateProfile(userSeq, request, image)
        return ResponseEntity.ok(BaseResponse.success(response, "프로필 설정이 완료되었습니다."))
    }
}