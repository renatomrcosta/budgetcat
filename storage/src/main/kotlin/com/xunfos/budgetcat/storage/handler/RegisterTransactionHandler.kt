package com.xunfos.budgetcat.storage.handler

import com.xunfos.budgetcat.storage.model.Transaction
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class RegisterTransactionHandler {
    operator fun invoke(transaction: Transaction) = runBlocking {
        when (transaction) {
            is Transaction.N26Transaction -> Unit
            is Transaction.GenericTransaction -> Unit
        }
    }
}
