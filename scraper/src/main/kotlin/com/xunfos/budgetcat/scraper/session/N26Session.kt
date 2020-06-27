package com.xunfos.budgetcat.scraper.session

import com.xunfos.budgetcat.scraper.client.N26AuthClient
import com.xunfos.budgetcat.scraper.model.BearerTokenResponse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Instant
import java.time.temporal.ChronoUnit

data class N26Session(var token: BearerTokenResponse) {
    private lateinit var refreshedAt: Instant
    private val refreshedAtMutex: Mutex =
        Mutex()

    private fun hasAuthorizedSession(): Boolean {
        return Instant.now().isBefore(
            refreshedAt.plus(
                token.expires_in.toLong(),
                ChronoUnit.SECONDS
            )
        )
    }

    suspend fun <T> executeTransaction(block: suspend (accessToken: String) -> T): T =
        coroutineScope {
            // Validate session?
            if (!hasAuthorizedSession()) {
                error("Invalid Session. Please retry")
            }
            block(this@N26Session.token.access_token.toString()).also {
                this@N26Session.updateTimeout()
            }
        }

    private suspend fun updateTimeout(): Unit = coroutineScope {
        refreshedAtMutex.withLock {
            this@N26Session.refreshedAt = Instant.now()
        }
    }

    companion object {
        suspend fun newSession(n26AuthClient: N26AuthClient): N26Session = coroutineScope {
            val mfaTokenResponse = n26AuthClient.requestMFAToken().awaitFirst()
            n26AuthClient.request2FA(mfaTokenResponse)
            val bearerTokenResponse = n26AuthClient.confirm2FA(mfaTokenResponse)

            N26Session(token = bearerTokenResponse).also {
                it.updateTimeout()
            }
        }
    }
}
