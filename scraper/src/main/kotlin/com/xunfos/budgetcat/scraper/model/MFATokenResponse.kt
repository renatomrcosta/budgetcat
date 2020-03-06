package com.xunfos.budgetcat.scraper.model

import java.util.UUID

data class MFATokenResponse(
    val mfaToken: UUID?,
    val error_description: String,
    val detail: String,
    val hostUrl: String,
    val type: String,
    val error: String,
    val title: String,
    val message: String,
    val userId: UUID?,
    val status: Int
)
