package br.com.devsrsouza.mcheads.server.database.exposed

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.lowerCase
import org.joda.time.DateTime
import java.util.*

object HeadTable : IntIdTable() {
    val name = text("name")
    val uuid = uuid("uuid")
    val mojangId = text("mojang_id").uniqueIndex()
    val category = enumeration("category", HeadCategory::class)
    val populated = varchar("populated", 255)
    val registerTime = datetime("register_time")
}

class HeadDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HeadDAO>(HeadTable) {
        fun containsString(str: String): SizedIterable<HeadDAO> {
            return find { HeadTable.name.lowerCase() regexp  ".*${str.toLowerCase()}.*"}
        }

        fun findByCategory(category: HeadCategory): SizedIterable<HeadDAO> {
            return find { HeadTable.category eq category }
        }
        
        fun findByMojangId(mojangId: String): HeadDAO? {
            return find { HeadTable.mojangId eq mojangId.toLowerCase() }
                .firstOrNull()
        }

        // null if the mojangId is already registered
        fun newHead(
            name: String,
            uuid: UUID,
            mojangId: String,
            category: HeadCategory,
            populatorName: String
        ): HeadDAO? {
            return if(findByMojangId(mojangId) == null) {
                new {
                    this.name = name
                    this.uuid = uuid
                    this.mojangId = mojangId.toLowerCase()
                    this.category = category
                    this.registerTime = DateTime.now()
                    this.populated = populatorName
                }
            } else null
        }
    }

    private var name by HeadTable.name
    private var uuid by HeadTable.uuid
    private var mojangId by HeadTable.mojangId
    private var category by HeadTable.category
    private var populated by HeadTable.populated
    private var registerTime by HeadTable.registerTime

    fun asHead() = Head(id.value, name, uuid.toString(), mojangId, category)
}