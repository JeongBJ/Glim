package com.jeongbj.glim.info.service

import com.jeongbj.glim.info.dto.InfoResponse
import com.jeongbj.glim.info.repository.InfoQueryRepository
import org.springframework.stereotype.Service


@Service
class InfoService(
    private val infoQueryRepository: InfoQueryRepository
) {
    fun getInfo(userSeq: Long): InfoResponse {
        val result = infoQueryRepository.getInfo(userSeq)
        return result
    }
}