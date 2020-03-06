package com.xunfos.budgetcat.scraper

import com.xunfos.budgetcat.scraper.config.N26Config
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(N26Config::class)
@SpringBootApplication
class ScraperApplication

fun main(args: Array<String>) {
    runApplication<ScraperApplication>(*args)
}
