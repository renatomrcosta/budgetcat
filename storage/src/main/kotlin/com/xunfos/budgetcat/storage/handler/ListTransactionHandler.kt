package com.xunfos.budgetcat.storage.handler

import com.xunfos.budgetcat.storage.model.Transaction
import com.xunfos.budgetcat.storage.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class ListTransactionHandler(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(): List<Transaction> = withContext(Dispatchers.IO) {
        transactionRepository.readTransactions()
    }
}
