package com.jeongbj.glim.info.controller

import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.info.dto.InfoResponse
import com.jeongbj.glim.info.service.InfoService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/info")
class InfoController(
    private val infoService: InfoService
) {

    @GetMapping
    fun getInfo(@AuthenticationPrincipal userSeq: Long)
    : ResponseEntity<BaseResponse<InfoResponse>> {
        val data = infoService.getInfo(userSeq)
        return ResponseEntity.ok(BaseResponse.success(data, "내 정보 조회 성공"))
    }
}