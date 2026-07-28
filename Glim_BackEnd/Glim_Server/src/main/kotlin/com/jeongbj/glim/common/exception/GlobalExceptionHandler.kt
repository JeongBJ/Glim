package com.jeongbj.glim.common.exception

import com.jeongbj.glim.common.response.BaseResponse
import com.oracle.bmc.model.BmcException
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

    @ExceptionHandler(UserNotFoundException::class)
    fun handleNotFound(e: UserNotFoundException): ResponseEntity<BaseResponse<Unit>> =
        ResponseEntity.status(404)
            .body(BaseResponse.error(404, e.message ?: "User Not Found"))

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(e: UnauthorizedException): ResponseEntity<BaseResponse<Unit>> =
        ResponseEntity.status(401)
            .body(BaseResponse.error(401, e.message ?: "Unauthorized"))

    @ExceptionHandler(BmcException::class)
    fun handleBmcException(e: BmcException): ResponseEntity<BaseResponse<Unit>> =
        ResponseEntity.internalServerError()
            .body(BaseResponse.error(500, "${e.message}"))

}