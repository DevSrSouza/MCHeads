package br.com.devsrsouza.mcheads.client

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory

const val API_URL = "http://API-DAS-HEADS.com.br"

interface IHeadApiService {
    suspend fun search(name: String): List<Head>

    suspend fun searchCategory(category: HeadCategory): List<Head>

    // Returns a PNG in a ByteArray or null if the head doesn't exist
    suspend fun getRender(headId: Int): ByteArray?
}

expect object HeadApi {
    val service: IHeadApiService
}