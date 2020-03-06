package com.xunfos.budgetcat.scraper.controller

import com.xunfos.budgetcat.scraper.client.N26Client
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ScrapeController(
    private val n26Client: N26Client
) {

    @GetMapping("/scrape")
    fun scrape(): ResponseEntity<String> {
        runBlocking {
            n26Client.fetchTransactions().also(::println)
        }
        return ResponseEntity.ok("Good")
    }
}
