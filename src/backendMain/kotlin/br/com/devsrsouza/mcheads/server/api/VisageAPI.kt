package br.com.devsrsouza.mcheads.server.api

import br.com.devsrsouza.mcheads.common.Head
import io.ktor.client.HttpClient
import io.ktor.client.request.get

object VisageAPI {
    const val VISAGE_HEAD_URL = "visage.domain.com/head/mojang:%s"

    suspend fun request3dHead(head: Head): ByteArray {
        val client = HttpClient()

        return client.get(VISAGE_HEAD_URL.format(head.mojangId))
    }

}