package br.com.devsrsouza.mcheads.server.database.populator

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

fun JsonPopulator(text: String) = JsonPopulator(
    text, PopulatorHead.serializer().list, { it }
)

open class JsonPopulator<T>(
    text: String,
    private val deserializer: DeserializationStrategy<List<T>>,
    private val mapping: (T) -> PopulatorHead
) : TextPopulator(text) {
    override val name: String = "json"

    override suspend fun headsToPopulate(): List<PopulatorHead> {
        return Json.parse(deserializer, text).map(mapping)
    }
}