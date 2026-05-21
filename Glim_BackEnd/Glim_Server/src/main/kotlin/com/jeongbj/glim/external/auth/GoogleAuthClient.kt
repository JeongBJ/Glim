package com.jeongbj.glim.external.auth

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class GoogleAuthClient(
    private val restClient: RestClient
) {
    fun getEmail(idToken: String): String {
        val response = restClient.get()
            .uri("https://oauth2.googleapis.com/tokeninfo?id_token=$idToken")
            .retrieve()
            .body<GoogleUserResponse>()
            ?: throw IllegalArgumentException("Google response is null")

        return response.email
            ?: throw IllegalArgumentException("Google email not found")
    }
}

data class GoogleUserResponse(
    val email: String?,
    val sub: String?
)