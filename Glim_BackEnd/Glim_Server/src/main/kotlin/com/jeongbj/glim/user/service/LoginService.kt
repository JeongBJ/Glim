package com.jeongbj.glim.user.service

import com.jeongbj.glim.auth.service.AuthService
import com.jeongbj.glim.user.client.GoogleAuthClient
import com.jeongbj.glim.user.client.KakaoAuthClient
import com.jeongbj.glim.user.dto.request.LoginRequest
import com.jeongbj.glim.user.dto.response.LoginResponse
import com.jeongbj.glim.user.entity.Provider
import com.jeongbj.glim.user.entity.User
import com.jeongbj.glim.user.mapper.toResponse
import com.jeongbj.glim.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LoginService(
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val kakaoAuthClient: KakaoAuthClient,
    private val googleAuthClient: GoogleAuthClient
) {
    fun googleLogin(loginRequest: LoginRequest): LoginResponse {
        val email = googleAuthClient.getEmail(loginRequest.idToken)
        return login(email, Provider.GOOGLE)
    }

    fun kakaoLogin(loginRequest: LoginRequest): LoginResponse {
        val email = kakaoAuthClient.getEmail(loginRequest.idToken)
        return login(email, Provider.KAKAO)
    }

    private fun login(email: String, provider: Provider): LoginResponse {
        val user = userRepository.findByEmailAndProvider(email, provider) ?: signUp(email, provider)
        val token = authService.createAuthToken(user.userSeq)
        return LoginResponse(token, user.toResponse())
    }

    private fun signUp(email: String, provider: Provider): User {
        return userRepository.save(User(email = email, provider = provider))
    }

}