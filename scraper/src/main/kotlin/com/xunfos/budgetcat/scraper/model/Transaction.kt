package com.xunfos.budgetcat.scraper.model

import java.sql.Timestamp
import java.util.UUID

sealed class Transaction {
    data class N26Transaction(
        val id: UUID,
        val userId: UUID,
        val type: String,
        val amount: Double,
        val currencyCode: String,
        val originalAmount: Double,
        val originalCurrency: String?,
        val exchangeRate: Double,
        val visibleTS: Timestamp,
        val merchantCity: String?,
        val merchantName: String?,
        val recurring: Boolean,
        val mcc: Int?,
        val mccGroup: Int?,
        val partnerAccountIsSepa: Boolean,
        val accountId: UUID,
        val category: String,
        val cardId: UUID?,
        val userCertified: Timestamp,
        val pending: Boolean,
        val transactionNature: String,
        val createdTS: Timestamp,
        val merchantCountry: Int,
        val merchantCountryCode: Int,
        val txnCondition: String?,
        val smartLinkId: UUID?,
        val linkId: UUID?,
        val confirmed: Timestamp?
    ) : Transaction()

    data class GenericTransaction(
        val response: String
    ) : Transaction()
}
