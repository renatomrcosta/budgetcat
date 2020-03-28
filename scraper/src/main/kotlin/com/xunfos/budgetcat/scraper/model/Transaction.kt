package com.xunfos.budgetcat.scraper.model

import java.math.BigInteger
import java.util.UUID

enum class TransactionCategory(val value: String) {
    FOOD_AND_GROCERIES("micro-v2-food-groceries")
}

data class Transaction(
    val id: UUID,
    val userId: UUID,
    // val type: "AA",
    val amount: Double,
    val currencyCode: String,
    val originalAmount: Double,
    val originalCurrency: String?,
    val exchangeRate: Double,
    val visibleTS: BigInteger,
    val merchantCity: String?,
    val merchantName: String?,
    val recurring: Boolean,
    val partnerAccountIsSepa: Boolean,
    val accountId: UUID,
    // val category: TransactionCategory,
    val category: String,
    val cardId: UUID?,
    val userCertified: BigInteger,
    val pending: Boolean,
    val transactionNature: String,
    val createdTS: BigInteger,
    val merchantCountry: Int,
    val txnCondition: String?,
    val smartLinkId: UUID?,
    val linkId: UUID?,
    val confirmed: BigInteger?
)
