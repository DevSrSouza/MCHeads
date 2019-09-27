package br.com.devsrsouza.mcheads.server

import br.com.devsrsouza.mcheads.common.HeadCategory
import br.com.devsrsouza.mcheads.server.manager.DatabaseManager
import br.com.devsrsouza.mcheads.server.manager.ImageManager
import br.com.devsrsouza.mcheads.server.manager.PopulatorManager
import br.com.devsrsouza.mcheads.server.route.category
import br.com.devsrsouza.mcheads.server.route.image
import br.com.devsrsouza.mcheads.server.route.search
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DataConversion
import io.ktor.locations.Locations
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.launch

suspend fun main() {
    embeddedServer(Netty, port = 8085) {
        install(DataConversion) {
            convert(HeadCategory::class) {
                encode {
                    if (it == null) emptyList()
                    else listOf((it as HeadCategory).name.toLowerCase())
                }
                decode { values, type ->
                    HeadCategory.values().first {
                        it.name.toLowerCase() in values.asSequence()
                            .map { it.toLowerCase() }
                    }
                }
            }
        }
        install(Locations)
        install(ContentNegotiation) {
            serialization()
        }

        val databaseManager = DatabaseManager()
        val imageManager = ImageManager(databaseManager)
        val populatorManager = PopulatorManager(databaseManager).apply {
            //registerPopulator(...)
        }

        launch {
            populatorManager.populateHeads()
        }

        routing {
            search(databaseManager)
            category(databaseManager)
            image(imageManager, databaseManager)
        }
    }.start(wait = true)
}