package com.xunfos.budgetcat.storage.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.xunfos.budgetcat.storage.config.DBConfig
import com.xunfos.budgetcat.storage.model.Transaction
import kotlinx.coroutines.coroutineScope
import org.postgresql.util.PGobject
import org.springframework.stereotype.Service
import java.sql.Connection
import java.sql.DriverManager
import java.util.UUID

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
        connection.prepareStatement(
            """
            INSERT INTO transaction (id, data) VALUES (?, ?)
        """.trimIndent()
        ).run {
            setObject(1, transaction.id)
            setObject(2, PGobject().apply {
                type = "jsonb"
                value = transaction.data.stringify()
            })
            execute()
        }
    }

    suspend fun readTransactions() = coroutineScope {
        val transactionList = mutableListOf<Transaction>()

        connection.prepareStatement(
            """
            SELECT id, data from transaction
        """.trimIndent()
        )
            .executeQuery()
            .run {
                while (next()) {
                    transactionList.add(
                        Transaction(
                            id = getObject("id") as UUID,
                            data = getString("data")
                        )
                    )
                }
            }

        transactionList
    }
}

private fun Any.stringify(): String = ObjectMapper().writeValueAsString(this)
