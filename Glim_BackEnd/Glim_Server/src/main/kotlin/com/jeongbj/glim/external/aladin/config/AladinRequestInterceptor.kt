package com.jeongbj.glim.external.aladin.config

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.stereotype.Component


@Component
class AladinRequestInterceptor(
    private val props: AladinProperties
) : RequestInterceptor {

    override fun apply(template: RequestTemplate) {
        template.query("ttbkey", props.ttbKey)
        template.query("output", props.output)
        template.query("Version", props.version)
        template.query("searchTarget", "Book")
    }
}
