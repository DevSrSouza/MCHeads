package br.com.devsrsouza.mcheads.server.database.populator

import java.io.File

open class FilePopulator(
    val file: File,
    val textPopulatorBuilder: (String) -> TextPopulator = { JsonPopulator(it) }
) : DatabasePopulator {
    override val name: String
        get() = "file:${file.nameWithoutExtension}"

    override suspend fun headsToPopulate(): List<PopulatorHead> {
        return textPopulatorBuilder(file.readText()).headsToPopulate()
    }
}