package br.com.devsrsouza.mcheads.server.manager

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import br.com.devsrsouza.mcheads.server.database.exposed.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class DatabaseManager {

    val database: Database

    init {
        val dataSource = HikariDataSource(HikariConfig().apply {
            jdbcUrl = "jdbc:h2:file:heads.h2.db"
        })

        database = Database.connect(dataSource)

        transaction(database) {
            SchemaUtils.create(HeadTable, HeadImageTable)
        }
    }

    suspend fun searchHeads(name: String): List<Head> {
        return suspendedTransactionAsync(db = database) {
            HeadDAO.containsString(name).map { it.asHead() }
        }.await()
    }

    /**
     * Null [category] if is all categories
     * -1 [limit] if doesn't have one of heads
     */
    suspend fun allHeads(
        category: HeadCategory?,
        page: Int,
        limit: Int
    ): List<Head> {
        if(page < 1) throw IllegalArgumentException("page can't be lower than one.")

        return suspendedTransactionAsync(db = database) {
            (if (category != null) {
                HeadDAO.findByCategory(category, page, limit)
            } else HeadDAO.all(page, limit)).map { it.asHead() }
        }.await()
    }

    suspend fun findHeadById(id: Int): Head? {
        return suspendedTransactionAsync(db = database) {
            HeadDAO.findById(id)?.asHead()
        }.await()
    }

    suspend fun findHeadImage(head: Head): HeadImage? {
        return suspendedTransactionAsync(db = database) {
            HeadImageDAO.findByHead(head)
        }.await()
    }

    // null if head doesn't exist
    suspend fun registerHeadImage(head: Head, image: ByteArray): HeadImage? {
        return suspendedTransactionAsync(db = database) {
            HeadImageDAO.newHead(image, head)
        }.await()
    }

    suspend fun registerHead(
        name: String,
        uuid: UUID,
        mojangId: String,
        category: HeadCategory,
        populatorName: String
    ): Head? {
        return suspendedTransactionAsync(db = database) {
            HeadDAO.newHead(
                name,
                uuid,
                mojangId,
                category,
                populatorName
            )?.asHead()
        }.await()
    }
}