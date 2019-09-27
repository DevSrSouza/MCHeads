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

@Location("/heads")
class heads(val category: HeadCategory? = null, val page: Int = 1, val limit: Int = -1)
// /heads?category=alphabet&page=1&limit=10

fun Route.category(manager: DatabaseManager) {
    get<heads> {
        withContext(Dispatchers.IO) {
            call.respond(manager.allHeads(it.category, it.page, it.limit))
        }
    }
}