package com.jeongbj.glim.auth.controller

import com.jeongbj.glim.auth.dto.request.RefreshTokenRequest
import com.jeongbj.glim.auth.dto.response.AuthTokenResponse
import com.jeongbj.glim.auth.service.AuthService
import com.jeongbj.glim.common.response.BaseResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/reissue")
    fun reissueAuthToken(
        @RequestBody refreshTokenRequest: RefreshTokenRequest
    ) : ResponseEntity<BaseResponse<AuthTokenResponse>> {
        val response = authService.reissueAuthToken(refreshTokenRequest.refreshToken)
        return ResponseEntity.ok(BaseResponse.success(response, "토큰 갱신이 완료되었습니다."))
    }

    @DeleteMapping("/logout")
    fun logout(@AuthenticationPrincipal userSeq: Long): ResponseEntity<BaseResponse<Unit>> {
        authService.logout(userSeq)
        return ResponseEntity.ok(BaseResponse.success(Unit))
    }

    @GetMapping("/resign")
    fun resign(@AuthenticationPrincipal userSeq: Long): ResponseEntity<BaseResponse<Unit>> {
        authService.resign(userSeq)
        return ResponseEntity.ok(BaseResponse.success(Unit))
    }
}