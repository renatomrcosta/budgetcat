package com.xunfos.budgetcat.scraper.client

import com.xunfos.budgetcat.scraper.config.N26Config
import com.xunfos.budgetcat.scraper.model.Transaction
import com.xunfos.budgetcat.scraper.session.N26Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import java.time.LocalDate
import java.time.ZoneOffset


@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
class N26Client(
    private val n26AuthClient: N26AuthClient,
    private val n26Config: N26Config
) : TransactionClient() {
    private val session: N26Session by lazy {
        runBlocking {
            N26Session.newSession(n26AuthClient = n26AuthClient)
        }
    }

    suspend fun fetchTransactions(
        startDate: LocalDate,
        endDate: LocalDate,
        limit: Int?
    ): List<Transaction> {
        return session.executeTransaction { token ->
            WebClient
                .builder()
                .baseUrl(n26Config.baseUrl)
                .defaultHeaders {
                    it.set(HttpHeaders.USER_AGENT, n26Config.userAgent)
                    it.setBearerAuth(token)
                }
                .build()
                .get()
                .uri {
                    it
                        .path(TRANSACTIONS_URI)
                        .queryParam(LIMIT_PARAMETER, (limit ?: DEFAULT_LIMIT))
                        .queryParam(FROM_PARAMETER, startDate.toEpochMilli())
                        .queryParam(TO_PARAMETER, endDate.toEpochMilli())
                        .build()
                }
                .awaitExchange()
                .bodyToFlux(Transaction.N26Transaction::class.java)
                .collectList()
                .awaitLast()
        }
    }

    companion object {
        const val TRANSACTIONS_URI = "api/smrt/transactions"
        const val FROM_PARAMETER = "from"
        const val TO_PARAMETER = "to"
        const val LIMIT_PARAMETER = "limit"
        const val DEFAULT_LIMIT = 100000
    }
}

private fun LocalDate.toEpochMilli(): Long =
    atStartOfDay().atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli()
