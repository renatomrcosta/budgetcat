package com.xunfos.budgetcat.storage.controller

import com.xunfos.budgetcat.storage.handler.RegisterTransactionHandler
import com.xunfos.budgetcat.storage.model.Transaction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/transaction")
class TransactionController(
    private val registerTransactionHandler: RegisterTransactionHandler
) {

    @PostMapping
    fun registerTransaction(@RequestBody transaction: Transaction): ResponseEntity.BodyBuilder {
        registerTransactionHandler(transaction = transaction)
        return ResponseEntity.ok()
    }
}
