package br.com.devsrsouza.mcheads.server.database.populator

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import kotlinx.serialization.Serializable
import kotlinx.serialization.list

@Serializable
data class PopulatorHead(
    val name: String,
    val uuid: String,
    val mojangId: String,
    val category: HeadCategory
)

interface DatabasePopulator {
    val name: String

    suspend fun headsToPopulate(): List<PopulatorHead>

    // Called when finish populate and returns populeted heads
    fun populetedHeads(heads: List<Head>) {}

    // Called when finish populate and returns duplicated heads (the same mojangId)
    fun duplicatedHeads(heads: List<PopulatorHead>) {}
}