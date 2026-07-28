package com.jeongbj.domain.user.model

import java.time.LocalDate

data class GlimContribution(
    val date: LocalDate,
    val count: Int
)
