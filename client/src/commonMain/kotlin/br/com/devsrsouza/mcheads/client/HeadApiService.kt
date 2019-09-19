package br.com.devsrsouza.mcheads.client

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory

interface IHeadApiService {
    suspend fun search(name: String): List<Head>

    suspend fun searchCategory(category: HeadCategory): List<Head>
}

expect object HeadApi {
    val service: IHeadApiService
}