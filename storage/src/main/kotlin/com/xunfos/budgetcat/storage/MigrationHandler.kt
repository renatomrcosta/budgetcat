package com.xunfos.budgetcat.storage

import com.xunfos.budgetcat.storage.config.DBConfig
import org.flywaydb.core.Flyway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MigrationHandler(
    private val dbConfig: DBConfig
) {
    val logger: Logger = LoggerFactory.getLogger(MigrationHandler::class.java.name)

    @PostConstruct
    fun migrateDB() {
        logger.info("Preparing DB Migration")
        try {
            Flyway
                .configure()
                .dataSource(dbConfig.url, dbConfig.username, dbConfig.password)
                .load()
                .run {
                    migrate()
                }
            logger.info("DB Migration completed successfully")
        } catch (e: Exception) {
            logger.error("error migrating the db", e)
        }
    }
}
