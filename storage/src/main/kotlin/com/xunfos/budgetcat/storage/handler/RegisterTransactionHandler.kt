package com.xunfos.budgetcat.storage.handler

import com.xunfos.budgetcat.storage.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RegisterTransactionHandler(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(id: UUID, transaction: Any) = runBlocking {
        transactionRepository.registerTransaction(
            id = id,
            transaction = transaction
        )
    }
}
