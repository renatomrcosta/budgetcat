package com.xunfos.budgetcat.storage.controller

import com.xunfos.budgetcat.storage.handler.ListTransactionHandler
import com.xunfos.budgetcat.storage.handler.RegisterTransactionHandler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/transaction")
class TransactionController(
    private val registerTransactionHandler: RegisterTransactionHandler,
    private val listTransactionHandler: ListTransactionHandler
) {
    @PostMapping
    fun registerTransaction(
        @RequestParam("id") id: UUID,
        @RequestBody transaction: Any
    ): ResponseEntity<UUID> {
        registerTransactionHandler(
            id = id,
            transaction = transaction
        )
        return ResponseEntity.ok(id)
    }

    @GetMapping
    fun listTransactions(): ResponseEntity<List<Any>> {
        val response = listTransactionHandler.invoke()
        return ResponseEntity.ok(response)
    }
}
