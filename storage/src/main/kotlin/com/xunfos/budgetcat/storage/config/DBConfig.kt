package com.xunfos.budgetcat.storage.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "db")
data class DBConfig(
    var username: String = "",
    var password: String = "",
    var database: String = "",
    var host: String = "",
    var port: Int = 5432
)
