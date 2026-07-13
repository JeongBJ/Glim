package com.jeongbj.glim.block.controller

import com.jeongbj.glim.block.service.BlockService
import com.jeongbj.glim.common.dto.CursorPage
import com.jeongbj.glim.common.dto.CursorRequest
import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.info.dto.QuoteThumbnailResponse
import com.jeongbj.glim.user.dto.response.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/block")
class BlockController(
    private val blockService: BlockService
) {

    @GetMapping("/quote/{quoteSeq}")
    fun blockQuote(
        @AuthenticationPrincipal userSeq: Long,
        @PathVariable quoteSeq: Long
    ): ResponseEntity<BaseResponse<Unit>> {
        blockService.blockQuote(userSeq, quoteSeq)
        return ResponseEntity.ok(BaseResponse.success(Unit, "글림 차단이 완료되었습니다."))
    }

    @GetMapping("/user/{blockedUserSeq}")
    fun blockUser(
        @AuthenticationPrincipal userSeq: Long,
        @PathVariable blockedUserSeq: Long
    ): ResponseEntity<BaseResponse<Unit>> {
        blockService.blockUser(userSeq, blockedUserSeq)
        return ResponseEntity.ok(BaseResponse.success(Unit, "사용자 차단이 완료되었습니다."))
    }

    @DeleteMapping("/quote/{quoteSeq}")
    fun unblockQuote(
        @AuthenticationPrincipal userSeq: Long,
        @PathVariable quoteSeq: Long
    ): ResponseEntity<BaseResponse<Unit>> {
        blockService.unblockQuote(userSeq, quoteSeq)
        return ResponseEntity.ok(BaseResponse.success(Unit, "글림 차단 해제가 완료되었습니다."))
    }

    @DeleteMapping("/user/{blockedUserSeq}")
    fun unblockUser(
        @AuthenticationPrincipal userSeq: Long,
        @PathVariable blockedUserSeq: Long
    ): ResponseEntity<BaseResponse<Unit>> {
        blockService.unblockUser(userSeq, blockedUserSeq)
        return ResponseEntity.ok(BaseResponse.success(Unit, "사용자 차단 해제가 완료되었습니다."))
    }

    @PostMapping("/quote")
    fun getBlockedQuotes(
        @AuthenticationPrincipal userSeq: Long,
        @RequestBody request: CursorRequest
    ) : ResponseEntity<BaseResponse<CursorPage<QuoteThumbnailResponse, Long>>> {
        val data = blockService.getBlockedQuotes(userSeq, request.cursor, request.size)
        return ResponseEntity.ok(BaseResponse.success(data, "차단된 글림 조회가 완료되었습니다."))

    }

    @GetMapping("/user")
    fun getBlockedUsers(
        @AuthenticationPrincipal userSeq: Long,
        @RequestBody request: CursorRequest
    ) : ResponseEntity<BaseResponse<CursorPage<UserResponse, Long>>> {
        val data = blockService.getBlockedUsers(userSeq, request.cursor, request.size)
        return ResponseEntity.ok(BaseResponse.success(data, "차단 사용자 조회가 완료되었습니다."))
    }
}