package com.xunfos.budgetcat.scraper.handler

import com.xunfos.budgetcat.scraper.client.N26Client
import com.xunfos.budgetcat.scraper.model.Transaction
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ScraperHandler(
    private val n26Client: N26Client
) {
    operator fun invoke(
        provider: String?,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Transaction> =
        runBlocking {
            if (startDate.isAfter(endDate)) {
                error("Invalid date parameters. Please retry")
            }
            when (provider?.toUpperCase()) {
                "N26", null -> n26Client.fetchTransactions(
                    startDate = startDate,
                    endDate = endDate
                )
                else -> error("Invalid provider")
            }
        }
}
