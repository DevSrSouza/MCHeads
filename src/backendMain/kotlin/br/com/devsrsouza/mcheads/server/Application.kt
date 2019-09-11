package br.com.devsrsouza.mcheads.server

import br.com.devsrsouza.mcheads.server.manager.DatabaseManager
import br.com.devsrsouza.mcheads.server.route.category
import br.com.devsrsouza.mcheads.server.route.search
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.locations.Locations
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8085) {
        install(Locations)
        install(ContentNegotiation) {
            serialization()
        }

        val databaseManager = DatabaseManager()

        routing {
            search(databaseManager)
            category(databaseManager)
        }
    }.start(wait = true)
}