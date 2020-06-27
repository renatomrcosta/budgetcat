package com.xunfos.budgetcat.scraper.handler

import com.xunfos.budgetcat.scraper.client.N26Client
import com.xunfos.budgetcat.scraper.model.Transaction
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ScraperHandler(
    private val n26Client: N26Client
) {
    suspend operator fun invoke(
        provider: String?,
        startDate: LocalDate,
        endDate: LocalDate,
        limit: Int?
    ): List<Transaction> {
        if (startDate.isAfter(endDate)) {
            error("Invalid date parameters. Please retry")
        }
        return when (provider?.toUpperCase()) {
            "N26", null -> n26Client.fetchTransactions(
                startDate = startDate,
                endDate = endDate,
                limit = limit
            )
            else -> error("Invalid provider")
        }
    }
}
