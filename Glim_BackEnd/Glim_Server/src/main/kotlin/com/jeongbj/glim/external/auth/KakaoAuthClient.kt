package com.jeongbj.glim.external.auth

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class KakaoAuthClient(
    private val restClient: RestClient
) {
    fun getEmail(accessToken: String): String {
        val response = restClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .header("Authorization", "Bearer $accessToken")
            .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
            .retrieve()
            .body<KakaoUserResponse>()
            ?: throw IllegalArgumentException("Kakao response is null")

        return response.kakaoAccount.email
            ?: throw IllegalArgumentException("Kakao email not found")
    }
}

data class KakaoUserResponse(
    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount
)

data class KakaoAccount(
    val email: String?
)