package br.com.devsrsouza.mcheads.server.sql

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.lowerCase

object HeadTable : IntIdTable() {
    val name = text("name")
    val uuid = uuid("uuid")
    val mojangId = text("mojang_id")
    val category = enumeration("category", HeadCategory::class)
}

class HeadDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HeadDAO>(HeadTable) {
        fun containsString(str: String): SizedIterable<HeadDAO> {
            return find { HeadTable.name.lowerCase() regexp  ".*${str.toLowerCase()}.*"}
        }

        fun findByCategory(category: HeadCategory): SizedIterable<HeadDAO> {
            return find { HeadTable.category eq category }
        }
    }

    var name by HeadTable.name
    var uuid by HeadTable.uuid
    var skinUrl by HeadTable.mojangId
    var category by HeadTable.category

    fun asHead() = Head(id.value, name, uuid.toString(), skinUrl, category)
}