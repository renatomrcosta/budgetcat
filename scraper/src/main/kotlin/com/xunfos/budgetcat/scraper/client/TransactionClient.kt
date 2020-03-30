package com.xunfos.budgetcat.scraper.client

import com.xunfos.budgetcat.scraper.model.Transaction

abstract class TransactionClient {
    fun fetchTransactions(): List<Transaction> = emptyList()
}
