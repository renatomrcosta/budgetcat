package com.xunfos.budgetcat.worker.client

import com.xunfos.budgetcat.worker.config.StorageConfig
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import java.util.UUID

@Service
class StorageClient(
    private val storageConfig: StorageConfig
) {
    private val webClient: WebClient by lazy {
        WebClient
            .builder()
            .baseUrl(storageConfig.host)
            .build()
    }

    suspend fun store(id: UUID, data: Any) = coroutineScope {
        webClient
            .post()
            .uri {
                it
                    .path(STORAGE_URI)
                    .queryParam(ID_PARAMETER, id)
                    .build()
            }
            .bodyValue(data)
            .headers {
                it.setBasicAuth(storageConfig.username, storageConfig.password)
            }
            .awaitExchange()

    }

    companion object {
        private const val STORAGE_URI = "transaction"
        private const val ID_PARAMETER = "id"
    }
}
