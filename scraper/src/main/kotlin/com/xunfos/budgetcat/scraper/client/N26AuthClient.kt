package com.xunfos.budgetcat.scraper.client

import com.xunfos.budgetcat.scraper.config.N26Config
import com.xunfos.budgetcat.scraper.model.BearerTokenResponse
import com.xunfos.budgetcat.scraper.model.MFATokenResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import reactor.core.publisher.Mono

@Service
class N26AuthClient(
    private val n26Config: N26Config
) {
    private val webClient: WebClient = WebClient.builder()
        .baseUrl(n26Config.baseUrl)
        .defaultHeaders {
            it.set(HttpHeaders.AUTHORIZATION, "Basic ${n26Config.authorizationToken}")
            it.set(HttpHeaders.USER_AGENT, n26Config.userAgent)
        }
        .build()

    suspend fun requestMFAToken(): Mono<MFATokenResponse> {
        return webClient
            .post()
            .uri(TOKEN_URI)
            .body(
                BodyInserters.fromMultipartData(
                    LinkedMultiValueMap<String, String>().apply {
                        add("grant_type", GRANT_TYPE_PASSWORD)
                        add("username", n26Config.username)
                        add("password", n26Config.password)
                    }
                ))
            .headers {
                it.set(
                    HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE
                )
            }
            .awaitExchange()
            .run {
                if (statusCode() != HttpStatus.FORBIDDEN) {
                    error("Unexpected response code ${statusCode()}:" + bodyToMono(String::class.java))
                }
                bodyToMono(MFATokenResponse::class.java)
            }
    }

    suspend fun request2FA(mfaTokenResponse: MFATokenResponse): Unit {
        return webClient
            .post()
            .uri(MFA_CHALLENGE_URI)
            .body(
                BodyInserters.fromValue(
                    """
                    {
	                    "challengeType": "oob",
	                    "mfaToken": "${mfaTokenResponse.mfaToken}"
                    }   
                """.trimIndent()
                )
            )
            .headers {
                it.add(
                    HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_JSON_VALUE
                )
            }
            .awaitExchange().run {
                if (statusCode().isError) {
                    error("Error requesting 2FA" + this.bodyToFlux(String::class.java))
                }
            }
    }

    suspend fun confirm2FA(mfaTokenResponse: MFATokenResponse): BearerTokenResponse {
        val client = webClient
            .post()
            .uri(TOKEN_URI)
            .body(
                BodyInserters.fromMultipartData(
                    LinkedMultiValueMap<String, String>().apply {
                        add("grant_type", GRANT_TYPE_MFA)
                        add("mfaToken", mfaTokenResponse.mfaToken.toString())
                    }
                ))
            .headers {
                it.set(
                    HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE
                )
            }

        // retries 12 times every 5 seconds (60 seconds total wait time)
        // until the login is approved in a authorized device (like the users phone)
        return withRetry(12) {
            val response = client
                .awaitExchange()

            if (response.statusCode().is2xxSuccessful) {
                response.bodyToMono(BearerTokenResponse::class.java)
                    .awaitFirst()
            } else {
                null
            }
        }
    }

    private suspend fun <T> withRetry(maxAttempts: Int, fn: suspend () -> T?): T {
        for (i in 1..maxAttempts) {
            val response = fn()

            if (response == null) {
                delay(5_000)
            } else {
                return response
            }
        }
        error("Max attempts reached")
    }

    companion object {
        const val GRANT_TYPE_PASSWORD = "password"
        const val GRANT_TYPE_MFA = "mfa_oob"
        const val TOKEN_URI = "oauth/token"
        const val MFA_CHALLENGE_URI = "api/mfa/challenge"
    }
}
