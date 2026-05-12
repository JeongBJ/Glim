package com.jeongbj.glim.common.exception

import com.jeongbj.glim.common.response.BaseResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .badRequest()
            .body(BaseResponse.error(400, e.message ?: "Bad Request"))
    }
}