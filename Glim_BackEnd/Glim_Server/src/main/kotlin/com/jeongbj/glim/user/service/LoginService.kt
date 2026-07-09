package com.jeongbj.glim.user.service

import com.jeongbj.glim.auth.service.AuthService
import com.jeongbj.glim.external.auth.GoogleAuthClient
import com.jeongbj.glim.external.auth.KakaoAuthClient
import com.jeongbj.glim.security.jwt.JwtProvider
import com.jeongbj.glim.user.dto.request.FcmTokenRequest
import com.jeongbj.glim.user.dto.request.LoginRequest
import com.jeongbj.glim.user.dto.response.LoginResponse
import com.jeongbj.glim.user.entity.FcmToken
import com.jeongbj.glim.user.entity.Provider
import com.jeongbj.glim.user.entity.User
import com.jeongbj.glim.user.mapper.toResponse
import com.jeongbj.glim.user.repository.FcmTokenRepository
import com.jeongbj.glim.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LoginService(
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val kakaoAuthClient: KakaoAuthClient,
    private val googleAuthClient: GoogleAuthClient,
    private val fcmTokenRepository: FcmTokenRepository,
    private val jwtProvider: JwtProvider
) {
    fun googleLogin(loginRequest: LoginRequest): LoginResponse {
        val email = googleAuthClient.getEmail(loginRequest.idToken)
        return login(email, loginRequest.fcmTokenRequest, Provider.GOOGLE)
    }

    fun kakaoLogin(loginRequest: LoginRequest): LoginResponse {
        val email = kakaoAuthClient.getEmail(loginRequest.idToken)
        return login(email, loginRequest.fcmTokenRequest, Provider.KAKAO)
    }

    fun autoLogin(loginRequest: LoginRequest): LoginResponse {
        val userSeq = jwtProvider.getUserId(loginRequest.idToken)
        val user = userRepository.findById(userSeq).orElseThrow()
        val token = authService.reissueAuthToken(loginRequest.idToken)
        saveOrUpdateToken(user, loginRequest.fcmTokenRequest)
        return LoginResponse(token, user.toResponse())
    }

    private fun login(email: String, fcmTokenRequest: FcmTokenRequest, provider: Provider): LoginResponse {
        val user = userRepository.findByEmailAndProvider(email, provider) ?: signUp(email, fcmTokenRequest.token, provider)
        val token = authService.createAuthToken(user.userSeq)
        saveOrUpdateToken(user, fcmTokenRequest)
        return LoginResponse(token, user.toResponse())
    }

    private fun saveOrUpdateToken(user: User, request: FcmTokenRequest) {
        val existing = fcmTokenRepository.findByToken(request.token)
        if (existing != null) {
            existing.updatePushEnabled(request.enabled)
        } else {
            fcmTokenRepository.save(FcmToken(user = user, token = request.token, pushEnabled = request.enabled))
        }
    }

    private fun signUp(email: String, fcmToken: String, provider: Provider): User {
        val user = userRepository.save(User(email = email, provider = provider))
        fcmTokenRepository.save(FcmToken(
            user = user,
            token = fcmToken
        ))
        return user
    }
}