package com.xunfos.budgetcat.storage.repository

import com.xunfos.budgetcat.storage.model.Transaction
import kotlinx.coroutines.coroutineScope
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.UUID

@Repository
interface TransactionCrud : CrudRepository<Transaction, UUID>

@Service
class TransactionRepository(
    private val transactionCrud: TransactionCrud
) {
    suspend fun registerTransaction(
        transaction: Transaction
    ) = coroutineScope {
        transactionCrud.save(transaction)
    }

    suspend fun readTransactions() = coroutineScope {
        emptyList<String>()
    }
}
