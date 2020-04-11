package com.xunfos.budgetcat.worker

import com.xunfos.budgetcat.worker.config.ScraperConfig
import com.xunfos.budgetcat.worker.config.StorageConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(ScraperConfig::class, StorageConfig::class)
@SpringBootApplication
class WorkerApplication

fun main(args: Array<String>) {
    runApplication<WorkerApplication>(*args)
}
