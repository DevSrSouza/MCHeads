package br.com.devsrsouza.mcheads.server.route

import br.com.devsrsouza.mcheads.common.HeadCategory
import br.com.devsrsouza.mcheads.server.manager.DatabaseManager
import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Location("/category/{category}")
class category(val category: HeadCategory)

fun Route.category(manager: DatabaseManager) {
    get<category> {
        withContext(Dispatchers.IO) {
            call.respond(manager.allHeadsFromCategory(it.category))
        }
    }
}