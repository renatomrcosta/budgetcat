package com.xunfos.budgetcat.worker.handler

import com.xunfos.budgetcat.worker.client.ScraperClient
import com.xunfos.budgetcat.worker.client.StorageClient
import com.xunfos.budgetcat.worker.model.WorkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class WorkerHandler(
    private val scraperClient: ScraperClient,
    private val storageClient: StorageClient
) {
    private val logger = LoggerFactory.getLogger(WorkerHandler::class.java)

    suspend operator fun invoke(workerOptions: WorkerOptions) = withContext(Dispatchers.IO) {
        with(workerOptions) {
            import(provider = provider, startDate = startDate, endDate = endDate, limit = limit)
        }
    }

    private suspend fun import(
        provider: WorkerOptions.Provider,
        startDate: LocalDate,
        endDate: LocalDate,
        limit: Int?
    ) = coroutineScope {
        logger.trace("Starting Import")

        logger.trace("Starting Scraper call")
        val scrapedTransactions = scraperClient.scrape(provider, startDate, endDate, limit)
        logger.trace("Scraper call successful")

        logger.trace("Starting Storage call")
        scrapedTransactions.forEach {
            try {
                storageClient.store(id = it.id, data = it)
            } catch (e: Exception) {
                logger.error("Transaction failed storage", e)
            }
        }
        logger.trace("Storage call successful")

    }
}
