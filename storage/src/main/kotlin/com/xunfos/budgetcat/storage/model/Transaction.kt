package com.xunfos.budgetcat.storage.model

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID

data class Transaction(
    val id: UUID,
    val data: String
) {
    companion object {
        fun create(id: UUID, data: Any): Transaction {
            val stringifiedData = ObjectMapper().writeValueAsString(data)
            return Transaction(id = id, data = stringifiedData)
        }
    }
}

