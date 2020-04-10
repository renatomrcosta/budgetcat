package com.xunfos.budgetcat.storage.repository

import com.xunfos.budgetcat.storage.config.DBConfig
import com.xunfos.budgetcat.storage.model.Transaction
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import java.sql.Connection
import java.sql.DriverManager

@Service
class TransactionRepository(
    private val dbConfig: DBConfig
) {
    private val connection: Connection by lazy {
        with(dbConfig) {
            DriverManager.getConnection(
                url, username, password
            )
        }
    }

    suspend fun registerTransaction(
        transaction: Transaction
    ) = coroutineScope {
        connection.prepareStatement("""
            INSERT INTO transaction (id, data) VALUES (?, to_jsonb(?))
        """.trimIndent()).run {
            setObject(1, transaction.id)
            setObject(2, transaction.data)
            execute()
        }
    }

    suspend fun readTransactions() = coroutineScope {
        emptyList<String>()
    }
}
