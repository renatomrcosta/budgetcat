package com.xunfos.budgetcat.storage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties
@SpringBootApplication
class StorageApplication

fun main(args: Array<String>) {
    runApplication<StorageApplication>(*args)
}
