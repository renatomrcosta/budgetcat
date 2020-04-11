package com.xunfos.budgetcat.worker.client

import com.xunfos.budgetcat.worker.config.ScraperConfig
import com.xunfos.budgetcat.worker.model.Transaction
import com.xunfos.budgetcat.worker.model.WorkerOptions
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import java.time.LocalDate

@Service
class ScraperClient(
    private val scraperConfig: ScraperConfig
) {
    suspend fun scrape(
        provider: WorkerOptions.Provider,
        startDate: LocalDate,
        endDate: LocalDate,
        limit: Int?
    ) = coroutineScope {
        WebClient
            .builder()
            .baseUrl(scraperConfig.host)
            .build()
            .post()
            .uri(
                SCRAPER_URI,
                mapOf<String, Any>(
                    PROVIDER_PARAMETER to provider,
                    LIMIT_PARAMETER to (limit ?: DEFAULT_LIMIT),
                    START_DATE_PARAMETER to startDate,
                    END_DATE_PARAMETER to endDate
                )
            )
            .headers {
                it.setBasicAuth(scraperConfig.username, scraperConfig.password)
            }
            .awaitExchange()
            .bodyToFlux(Transaction.N26Transaction::class.java)
            .collectList()
            .awaitLast()
    }

    companion object {
        private const val SCRAPER_URI = "scrape"
        private const val PROVIDER_PARAMETER = "provider"
        private const val START_DATE_PARAMETER = "start_date"
        private const val END_DATE_PARAMETER = "end_date"
        private const val LIMIT_PARAMETER = "limit"
        private const val DEFAULT_LIMIT = 2000
    }
}
