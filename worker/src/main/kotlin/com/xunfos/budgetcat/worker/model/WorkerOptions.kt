package com.xunfos.budgetcat.worker.model

import java.time.LocalDate

data class WorkerOptions(
    val operation: Operation,
    val provider: Provider,
    var startDate: LocalDate?,
    var endDate: LocalDate?,
    var limit: Int? = 2000
) {
    enum class Operation(val value: String) {
        IMPORT("IMPORT"),
        IMPORT_ALL("IMPORT_ALL")
    }

    enum class Provider(val value: String) {
        N26("N26")
    }
}
