package com.xunfos.budgetcat.scraper.client

import com.xunfos.budgetcat.scraper.config.N26Config
import com.xunfos.budgetcat.scraper.session.N26Session
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import java.time.LocalDate

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
class N26Client(
    private val n26AuthClient: N26AuthClient,
    private val n26Config: N26Config
) {
    private val session: N26Session by lazy {
        N26Session.newSession(n26AuthClient = n26AuthClient)
    }

    suspend fun fetchTransactions(
        startDate: LocalDate,
        endDate: LocalDate
    ): String = coroutineScope {
        session.executeTransaction { token ->
            WebClient
                .builder()
                .baseUrl(n26Config.baseUrl)
                .defaultHeaders {
                    it.set(HttpHeaders.USER_AGENT, n26Config.userAgent)
                    it.setBearerAuth(token)
                }
                .build()
                .get()
                .uri(
                    TRANSACTIONS_URI,
                    mapOf<String, Any>(
                        LIMIT_PARAMETER to LIMIT_PARAMETER_VALUE,
                        FROM_PARAMETER to startDate,
                        TO_PARAMETER to endDate
                    )
                )
                .awaitExchange()
                .bodyToMono(String::class.java)
                .awaitFirst()
        }
    }

    companion object {
        const val TRANSACTIONS_URI = "api/smrt/transactions"
        const val FROM_PARAMETER = "from"
        const val TO_PARAMETER = "to"
        const val LIMIT_PARAMETER = "limit"
        const val LIMIT_PARAMETER_VALUE = "2000"
    }
}
