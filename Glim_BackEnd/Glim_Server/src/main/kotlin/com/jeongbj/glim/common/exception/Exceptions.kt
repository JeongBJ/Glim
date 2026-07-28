package com.jeongbj.glim.common.exception

sealed class AppException(message: String) : RuntimeException(message)
class UserNotFoundException(message: String = "User Not Found") : AppException(message)
class UnauthorizedException(message: String = "Unauthorized") : AppException(message)