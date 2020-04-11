package com.xunfos.budgetcat.worker.handler

import com.xunfos.budgetcat.worker.client.ScraperClient
import com.xunfos.budgetcat.worker.client.StorageClient
import com.xunfos.budgetcat.worker.model.WorkerOptions
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class WorkerHandler(
    private val scraperClient: ScraperClient,
    private val storageClient: StorageClient
) {
    private val logger = LoggerFactory.getLogger(WorkerHandler::class.java)

    operator fun invoke(workerOptions: WorkerOptions) = runBlocking {
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
        logger.info("Starting Import")

        logger.info("Starting Scraper call")
        val scrapedTransactions = scraperClient.scrape(provider, startDate, endDate, limit)
        logger.info("Scraper call successful")

        logger.info("Starting Storage call")
        scrapedTransactions.forEach {
            try {
                storageClient.store(id = it.id, data = it)
            } catch (e: Exception) {
                logger.error("Transaction failed storage", e)
            }
        }
        logger.info("Storage call successful")

    }
}
