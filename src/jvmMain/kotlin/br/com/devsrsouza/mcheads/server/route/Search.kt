package br.com.devsrsouza.mcheads.server.route

import br.com.devsrsouza.mcheads.server.manager.DatabaseManager
import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Location("/search/{name}")
class search(val name: String)

fun Route.search(manager: DatabaseManager) {
    get<search> {
        withContext(Dispatchers.IO) {
            call.respond(manager.searchHeads(it.name))
        }
    }
}