package com.jeongbj.domain.auth.storage

interface AccessTokenStorage {
    fun getAccessToken(): String?
    fun setAccessToken(token: String)
    fun clear()
}