package com.jeongbj.glim.auth.service

import com.jeongbj.glim.auth.dto.response.AuthTokenResponse
import com.jeongbj.glim.auth.repository.AuthRedisRepository
import com.jeongbj.glim.block.repository.BlockedQuoteRepository
import com.jeongbj.glim.block.repository.BlockedUserRepository
import com.jeongbj.glim.infra.bucket.BucketService
import com.jeongbj.glim.like.repository.LikeRepository
import com.jeongbj.glim.security.jwt.JwtProvider
import com.jeongbj.glim.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val jwtProvider: JwtProvider,
    private val authRepository: AuthRedisRepository,
    private val userRepository: UserRepository,
    private val likeRepository: LikeRepository,
    private val bucketService: BucketService,
    private val blockedQuoteRepository: BlockedQuoteRepository,
    private val blockedUserRepository: BlockedUserRepository
) {

    fun createAuthToken(userSeq: Long): AuthTokenResponse {
        val now = System.currentTimeMillis()
        val accessToken = jwtProvider.createAccessToken(userSeq, now)
        val refreshToken = jwtProvider.createRefreshToken(userSeq, now)

        authRepository.save(
            userSeq = userSeq,
            refreshToken = refreshToken
        )

        return AuthTokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun reissueAuthToken(refreshToken: String): AuthTokenResponse {
        if (!jwtProvider.validateRefreshToken(refreshToken)) throw IllegalArgumentException("Invalid RefreshToken")
        val userSeq = jwtProvider.getUserId(refreshToken)
        val savedToken = authRepository.find(userSeq) ?: throw IllegalArgumentException("Not Exist RefreshToken")
        if (savedToken != refreshToken) throw IllegalArgumentException("Invalid RefreshToken!")

        return createAuthToken(userSeq)
    }

    fun logout(userSeq: Long) {
        authRepository.delete(userSeq)
    }

    fun resign(userSeq: Long) {
        val user = userRepository.findById(userSeq).orElseThrow()
        val images = (listOf(user.imageUrl) + user.quotes.map { it.imageUrl }).filterNotNull()
        authRepository.delete(userSeq)
        likeRepository.deleteAllByUser_UserSeq(userSeq)
        blockedQuoteRepository.deleteAllByUser_UserSeq(userSeq)
        blockedUserRepository.deleteAllByUser_UserSeq(userSeq)
        blockedUserRepository.deleteAllByBlockedUser_UserSeq(userSeq)
        userRepository.deleteById(userSeq)
        bucketService.batchDelete(images)
    }
}