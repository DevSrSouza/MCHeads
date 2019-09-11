package br.com.devsrsouza.mcheads.server.manager

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import br.com.devsrsouza.mcheads.server.sql.HeadDAO
import br.com.devsrsouza.mcheads.server.sql.HeadTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseManager {

    val database: Database

    init {
        val dataSource = HikariDataSource(HikariConfig().apply {
            jdbcUrl = "jdbc:h2:file:heads.h2.db"
        })

        database = Database.connect(dataSource)

        transaction(database) {
            SchemaUtils.create(HeadTable)
        }
    }

    suspend fun searchHeads(name: String): List<Head> {
        return suspendedTransactionAsync(db = database) {
            HeadDAO.containsString(name).map { it.asHead() }
        }.await()
    }

    suspend fun allHeadsFromCategory(category: HeadCategory): List<Head> {
        return suspendedTransactionAsync(db = database) {
            HeadDAO.findByCategory(category).map { it.asHead() }
        }.await()
    }
}