package com.jeongbj.glim.external.ai.gradio.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GradioEventResponse(
    @JsonProperty("event_id")
    val eventId: String
)