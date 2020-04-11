package com.xunfos.budgetcat.worker.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "storage")
data class StorageConfig(
    val host: String,
    val username: String,
    val password: String
)
