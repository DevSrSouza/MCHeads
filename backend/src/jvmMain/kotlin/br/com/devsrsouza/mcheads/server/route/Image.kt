package br.com.devsrsouza.mcheads.server.route

import br.com.devsrsouza.mcheads.server.manager.DatabaseManager
import br.com.devsrsouza.mcheads.server.manager.ImageManager
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.response.respondBytes
import io.ktor.response.respondText
import io.ktor.routing.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Location("image/{headId}")
class image(val headId: Int)

fun Route.image(
    imageManager: ImageManager,
    databaseManager: DatabaseManager
) {
    get<image> {
        val image = withContext(Dispatchers.IO) {
            databaseManager.findHeadById(it.headId)?.let {
                imageManager.headImageFromHead(it)
            }
        }
        if(image != null) {
            call.respondBytes(image.image, ContentType.Image.PNG)
        } else call.respondText("Image not found", status = HttpStatusCode.NotFound)
    }
}