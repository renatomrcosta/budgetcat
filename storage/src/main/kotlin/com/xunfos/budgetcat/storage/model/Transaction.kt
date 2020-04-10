package com.xunfos.budgetcat.storage.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Entity
data class Transaction(
    @Id
    val id: UUID,

    @Column(columnDefinition = "jsonb")
    val data:
) {
    constructor() : this(id = UUID.randomUUID(), data = null )

    companion object {
        fun create(id: UUID, transaction: Any): Transaction {
            return Transaction(
                id = id,
                data = transaction
            )
        }
    }
}

