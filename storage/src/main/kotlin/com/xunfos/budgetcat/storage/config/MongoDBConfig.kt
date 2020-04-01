package com.xunfos.budgetcat.storage.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration()
@ConfigurationProperties(prefix = "n26")
data class MongoDBConfig(
    val host: String = "",
    val port: Int = 27017
)
