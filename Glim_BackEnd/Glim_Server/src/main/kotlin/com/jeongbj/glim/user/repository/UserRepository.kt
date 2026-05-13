package com.jeongbj.glim.user.repository

import com.jeongbj.glim.user.entity.Provider
import com.jeongbj.glim.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmailAndProvider(email: String, provider: Provider): User?

}