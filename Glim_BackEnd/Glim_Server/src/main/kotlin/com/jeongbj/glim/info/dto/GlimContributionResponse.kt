package com.jeongbj.glim.info.dto

import java.time.LocalDate

data class GlimContributionResponse(
    val date: LocalDate,
    val count: Int
)
