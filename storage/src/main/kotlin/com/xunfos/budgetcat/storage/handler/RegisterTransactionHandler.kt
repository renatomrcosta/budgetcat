package com.xunfos.budgetcat.storage.handler

import com.xunfos.budgetcat.storage.model.Transaction
import com.xunfos.budgetcat.storage.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RegisterTransactionHandler(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(id: UUID, transaction: Any) = withContext(Dispatchers.IO) {
        transactionRepository.registerTransaction(
            transaction = Transaction(id = id, data = transaction)
        )
    }
}
