package com.jeongbj.glim.info.controller

import com.jeongbj.glim.common.dto.CursorPage
import com.jeongbj.glim.common.dto.CursorRequest
import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.info.dto.InfoResponse
import com.jeongbj.glim.info.dto.QuoteThumbnailResponse
import com.jeongbj.glim.info.service.InfoService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/info")
class InfoController(
    private val infoService: InfoService
) {

    @GetMapping("/{userSeq}")
    fun getInfo(
        @PathVariable userSeq: Long,
        @AuthenticationPrincipal myUserSeq: Long
    ) : ResponseEntity<BaseResponse<InfoResponse>> {
        val currentUserSeq = if (userSeq == 0L) myUserSeq else userSeq
        val data = infoService.getInfo(currentUserSeq)
        return ResponseEntity.ok(BaseResponse.success(data, "내 정보 조회 성공"))
    }

    @GetMapping("/liked/{userSeq}")
    fun getLikedQuotes(
        @PathVariable userSeq: Long,
        @AuthenticationPrincipal myUserSeq: Long,
        request: CursorRequest
    ): ResponseEntity<BaseResponse<CursorPage<QuoteThumbnailResponse, Long>>> {
        val currentUserSeq = if (userSeq == 0L) myUserSeq else userSeq
        val data = infoService.getLikedQuotes(currentUserSeq, request.cursor, request.size)
        return ResponseEntity.ok(BaseResponse.success(data, "좋아요 한 글림 조회 성공"))
    }

    @GetMapping("/quotes/{userSeq}")
    fun getMyQuotes(
        @PathVariable userSeq: Long,
        @AuthenticationPrincipal myUserSeq: Long,
        request: CursorRequest
    ): ResponseEntity<BaseResponse<CursorPage<QuoteThumbnailResponse, Long>>> {
        val currentUserSeq = if (userSeq == 0L) myUserSeq else userSeq
        val data = infoService.getUserQuotes(currentUserSeq, request.cursor, request.size)
        return ResponseEntity.ok(BaseResponse.success(data, "내 글림 조회 성공"))
    }

    @GetMapping("/liked/{userSeq}")
    fun getUserLikedQuotes(
        @PathVariable userSeq: Long,
        request: CursorRequest
    ): ResponseEntity<BaseResponse<CursorPage<QuoteThumbnailResponse, Long>>> {
        val data = infoService.getLikedQuotes(userSeq, request.cursor, request.size)
        return ResponseEntity.ok(BaseResponse.success(data, "좋아요 한 글림 조회 성공"))
    }

    @GetMapping("/quotes/{userSeq}")
    fun getUserQuotes(
        @PathVariable userSeq: Long,
        request: CursorRequest
    ): ResponseEntity<BaseResponse<CursorPage<QuoteThumbnailResponse, Long>>> {
        val data = infoService.getUserQuotes(userSeq, request.cursor, request.size)
        return ResponseEntity.ok(BaseResponse.success(data, "유저 글림 조회 성공"))
    }
}