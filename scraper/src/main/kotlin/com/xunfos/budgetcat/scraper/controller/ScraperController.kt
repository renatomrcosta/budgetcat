package com.xunfos.budgetcat.scraper.controller

import com.xunfos.budgetcat.scraper.handler.ScraperHandler
import com.xunfos.budgetcat.scraper.model.Transaction
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class ScraperController(
    private val scraperHandler: ScraperHandler
) {
    @GetMapping("/scrape")
    fun scrape(
        @RequestParam("provider") provider: String?,
        @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate,
        @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate
    ): ResponseEntity<List<Transaction>> {
        val response = scraperHandler(
            provider = provider,
            startDate = startDate,
            endDate = endDate
        )
        return ResponseEntity.ok(response)
    }
}
