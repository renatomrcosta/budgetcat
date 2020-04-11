package com.xunfos.budgetcat.storage.handler

import com.xunfos.budgetcat.storage.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class ListTransactionHandler(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke() = runBlocking {
        transactionRepository.readTransactions()
    }
}
