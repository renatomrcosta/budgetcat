package com.xunfos.budgetcat.scraper.model

import java.util.UUID

data class BearerTokenResponse(
    val access_token: UUID,
    val token_type: String,
    val refresh_token: UUID,
    val expires_in: Int,
    val host_url: String
)
