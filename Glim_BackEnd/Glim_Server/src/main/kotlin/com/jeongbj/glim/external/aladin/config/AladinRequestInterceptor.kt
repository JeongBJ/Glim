package com.jeongbj.glim.external.aladin.config

import feign.RequestInterceptor
import feign.RequestTemplate
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component


@Component
class AladinRequestInterceptor(
    private val props: AladinProperties
) : RequestInterceptor {
    private val logger = KotlinLogging.logger {  }

    override fun apply(template: RequestTemplate) {
        logger.info { "Aladin API - $template" }
        template.query("ttbkey", props.ttbKey)
        template.query("output", props.output)
        template.query("Version", props.version)
        template.query("searchTarget", "Book")
    }
}
