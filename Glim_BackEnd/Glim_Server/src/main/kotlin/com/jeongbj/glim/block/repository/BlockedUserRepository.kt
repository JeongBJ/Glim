package com.jeongbj.glim.block.repository

import com.jeongbj.glim.block.entity.BlockedUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BlockedUserRepository: JpaRepository<BlockedUser, Long> {
    fun deleteByUser_UserSeqAndBlockedUser_UserSeq(userSeq: Long, blockedUserSeq: Long): Optional<BlockedUser>
}