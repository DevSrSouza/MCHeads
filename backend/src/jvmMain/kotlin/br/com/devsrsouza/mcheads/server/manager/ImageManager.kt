package br.com.devsrsouza.mcheads.server.manager

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.server.api.VisageAPI
import br.com.devsrsouza.mcheads.server.database.exposed.HeadImage

class ImageManager(
    private val databaseManager: DatabaseManager
) {
    suspend fun headImageFromHead(head: Head): HeadImage {
        return databaseManager.findHeadImage(head)
            ?: VisageAPI.request3dHead(head).let {
            databaseManager.registerHeadImage(head, it)
        } ?: throw Throwable("Head id specified doesn't exist in database.")
    }
}