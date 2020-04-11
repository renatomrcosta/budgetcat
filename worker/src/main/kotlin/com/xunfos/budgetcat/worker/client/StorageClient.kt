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
    suspend fun store(id: UUID, data: Any) = coroutineScope {
        WebClient
            .builder()
            .baseUrl(storageConfig.host)
            .build()
            .post()
            .uri(
                STORAGE_URI,
                mapOf<String, Any>(
                    ID_PARAMETER to id
                )
            )
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
