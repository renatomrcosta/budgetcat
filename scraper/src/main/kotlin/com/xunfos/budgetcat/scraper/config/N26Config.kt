package com.xunfos.budgetcat.scraper.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "n26")
data class N26Config(
    var baseUrl: String = "",
    var username: String = "",
    var password: String = "",
    var authorizationToken: String = "",
    var userAgent: String = ""
)
