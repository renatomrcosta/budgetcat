package com.xunfos.budgetcat.storage.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

    @GetMapping("/health")
    suspend fun health() = "OK"
}
