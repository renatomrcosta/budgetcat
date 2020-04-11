package com.xunfos.budgetcat.worker.controller

import com.xunfos.budgetcat.worker.handler.WorkerHandler
import com.xunfos.budgetcat.worker.model.WorkerOptions
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class WorkerController(
    private val workerHandler: WorkerHandler
) {
    @PostMapping("/work")
    fun work(
        @RequestParam("provider") provider: String?,
        @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate,
        @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate,
        @RequestParam("limit") limit: Int?
    ) {
        workerHandler(
            WorkerOptions(
                provider = WorkerOptions.Provider.valueOf((provider?.toUpperCase() ?: "N26")),
                startDate = startDate,
                endDate = endDate,
                limit = limit
            )
        )
    }
}
