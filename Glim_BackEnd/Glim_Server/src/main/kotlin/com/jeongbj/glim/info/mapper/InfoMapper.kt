package com.jeongbj.glim.info.mapper

import com.jeongbj.glim.info.dto.GlimContributionResponse
import com.jeongbj.glim.info.dto.InfoResponse
import com.jeongbj.glim.info.dto.InfoUserProjection

fun InfoUserProjection.toResponse(contributions: List<GlimContributionResponse>): InfoResponse = InfoResponse(
    user = user,
    numLikes = numLikes,
    numQuotes = numQuotes,
    contributions = contributions
)