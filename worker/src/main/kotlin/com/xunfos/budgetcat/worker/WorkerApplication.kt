package com.xunfos.budgetcat.worker

import com.xunfos.budgetcat.worker.config.ScraperConfig
import com.xunfos.budgetcat.worker.config.StorageConfig
import com.xunfos.budgetcat.worker.handler.WorkerHandler
import com.xunfos.budgetcat.worker.model.WorkerOptions
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import java.time.LocalDate

@EnableConfigurationProperties(ScraperConfig::class, StorageConfig::class)
@SpringBootApplication
class WorkerApplication(
    private val workerHandler: WorkerHandler
) : ApplicationRunner {
    private val logger = LoggerFactory.getLogger(WorkerApplication::class.java)

    override fun run(args: ApplicationArguments) {
        logger.info("Running Worker")
        val options = parseArgs(args)



        logger.info("Worker finished successfully")
    }

    private fun parseArgs(args: ApplicationArguments): WorkerOptions {
        val operation =
            if (args.nonOptionArgs.size == 1) {
                WorkerOptions.Operation.valueOf(args.nonOptionArgs.first().toUpperCase())
            } else {
                error("Invalid operation argument. Choose one operation (${WorkerOptions.Operation.values()})")
            }

        val provider = if (args.containsOption("provider")) {
            WorkerOptions.Provider.valueOf(
                args.getOptionValues("provider").first().toUpperCase()
            )
        } else WorkerOptions.Provider.N26

        var startDate: LocalDate? = null
        var endDate: LocalDate? = null

        if (operation == WorkerOptions.Operation.IMPORT) {
            startDate =
                if (args.containsOption("start_date")) {
                    args.getOptionValues("start_date").first().toLocalDate()
                } else {
                    error("Provide start_date for operation IMPORT")
                }

            endDate =
                if (args.containsOption("end_date")) {
                    args.getOptionValues("end_date").first().toLocalDate()
                } else {
                    error("Provide end_date for operation IMPORT")
                }
        }
        val limit = if (args.containsOption("limit")) {
            args.getOptionValues("limit").first().toInt()
        } else 2000

        return WorkerOptions(
            operation = operation,
            provider = provider,
            startDate = startDate,
            endDate = endDate,
            limit = limit
        )
    }

    fun main(args: Array<String>) {
        runApplication<WorkerApplication>(*args)
    }
}

private fun String.toLocalDate(): LocalDate? = LocalDate.parse(this)
