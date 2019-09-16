package br.com.devsrsouza.mcheads.server

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.server.manager.DatabaseManager
import br.com.devsrsouza.mcheads.server.manager.ImageManager
import br.com.devsrsouza.mcheads.server.manager.PopulatorManager
import br.com.devsrsouza.mcheads.server.route.category
import br.com.devsrsouza.mcheads.server.route.image
import br.com.devsrsouza.mcheads.server.route.search
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.locations.Locations
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.launch

suspend fun main() {
    embeddedServer(Netty, port = 8085) {
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