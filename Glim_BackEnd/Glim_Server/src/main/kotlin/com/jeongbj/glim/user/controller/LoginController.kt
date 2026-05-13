package com.jeongbj.glim.user.controller

import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.user.dto.request.LoginRequest
import com.jeongbj.glim.user.dto.response.LoginResponse
import com.jeongbj.glim.user.service.LoginService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(
    private val loginService: LoginService
) {
    @PostMapping("/google")
    fun googleLogin(@RequestBody loginRequest: LoginRequest): ResponseEntity<BaseResponse<LoginResponse>> {
        val response = loginService.googleLogin(loginRequest)
        return ResponseEntity.ok(BaseResponse.success(response))
    }

    @PostMapping("/kakao")
    fun kakaoLogin(@RequestBody loginRequest: LoginRequest): ResponseEntity<BaseResponse<LoginResponse>> {
        val response = loginService.kakaoLogin(loginRequest)
        return ResponseEntity.ok(BaseResponse.success(response))
    }
}