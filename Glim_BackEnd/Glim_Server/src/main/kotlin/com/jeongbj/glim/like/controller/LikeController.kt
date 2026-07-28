package com.jeongbj.glim.like.controller

import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.like.service.LikeService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/like")
class LikeController(
    private val likeService: LikeService
) {

    @GetMapping("/{quoteSeq}")
    fun toggleLikeQuote(@AuthenticationPrincipal userSeq: Long, @PathVariable quoteSeq: Long): ResponseEntity<BaseResponse<Unit>> {
        val msg = if (likeService.toggleLikeQuote(userSeq, quoteSeq)) "좋아요" else "좋아요 취소"
        return ResponseEntity.ok(BaseResponse.success(Unit, msg))
    }
}