package br.com.devsrsouza.mcheads.server.manager

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.server.database.populator.DatabasePopulator
import br.com.devsrsouza.mcheads.server.database.populator.PopulatorHead
import java.util.*

class PopulatorManager(
    private val databaseManager: DatabaseManager
) {

    private val populators = mutableSetOf<DatabasePopulator>()

    fun registerPopulator(populator: DatabasePopulator): Boolean {
        return populators.add(populator)
    }

    suspend fun populateHeads() {
        for (populator in populators) {
            val populated = mutableListOf<Head>()
            val notPopulated = mutableListOf<PopulatorHead>()

            for (head in populator.headsToPopulate()) {
                val newHead = insertNewHead(populator, head)

                if(newHead != null) populated.add(newHead)
                else notPopulated.add(head)
            }

            populator.populetedHeads(populated)
            populator.duplicatedHeads(notPopulated)
        }
    }

    private suspend fun insertNewHead(
        populator: DatabasePopulator,
        head: PopulatorHead
    ): Head? {
        return databaseManager.registerHead(
            head.name,
            UUID.fromString(head.uuid),
            head.mojangId,
            head.category,
            populator.name
        )
    }
}