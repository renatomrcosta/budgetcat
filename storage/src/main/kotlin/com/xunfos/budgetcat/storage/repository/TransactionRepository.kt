package com.xunfos.budgetcat.storage.repository

import com.xunfos.budgetcat.storage.config.DBConfig
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TransactionRepository(
    private val dbConfig: DBConfig
) {
    suspend fun registerTransaction(
        id: UUID,
        transaction: Any
    ): Unit = coroutineScope {
        println(dbConfig.database)
        println(dbConfig.host)
        println(dbConfig.port)
        println(dbConfig.username)
        println(dbConfig.password)
    }

    suspend fun readTransactions() = coroutineScope {
        emptyList<String>()
    }
}
