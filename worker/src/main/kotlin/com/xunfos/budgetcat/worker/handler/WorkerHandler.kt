package com.xunfos.budgetcat.worker.handler

import com.xunfos.budgetcat.worker.client.ScraperClient
import com.xunfos.budgetcat.worker.client.StorageClient
import com.xunfos.budgetcat.worker.model.WorkerOptions
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class WorkerHandler(
    private val scraperClient: ScraperClient,
    private val storageClient: StorageClient
) {

    fun runOperation(workerOptions: WorkerOptions) = runBlocking{
        when (workerOptions.operation) {
            WorkerOptions.Operation.IMPORT -> import(
                provider = workerOptions.provider,
                startDate = workerOptions.startDate!!,
                endDate = workerOptions.endDate!!,
                limit = workerOptions.limit
            )
            WorkerOptions.Operation.IMPORT_ALL -> importAll(
                provider = workerOptions.provider,
                limit = workerOptions.limit
            )
        }
    }

    private suspend fun import(provider: WorkerOptions.Provider, startDate: LocalDate, endDate: LocalDate, limit: Int?) = coroutineScope {
        val scrapedTransactions = scraperClient.scrape(provider, startDate, endDate, limit)

        scrapedTransactions.forEach {
            storageClient.store(id = it.id, data = it)
        }
    }
    private fun importAll(provider: WorkerOptions.Provider, limit: Int?) {
        TODO("ITerate all months since 2019 and import every month until current date")
    }
}
