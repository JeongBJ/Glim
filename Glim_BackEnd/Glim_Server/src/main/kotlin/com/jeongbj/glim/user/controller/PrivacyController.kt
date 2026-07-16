package com.jeongbj.glim.user.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/privacy")
class PrivacyController {

    @GetMapping
    fun getPrivacyPolicy(): String {
        return "privacy"
    }
}