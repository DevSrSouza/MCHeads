package br.com.devsrsouza.mcheads.client

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory

interface IHeadApiService {
    /**
     * Returns a list of [Head] that contains [search] in the [Head.name]
     */
    suspend fun search(search: String): List<Head>

    /**
     * Returns all heads filtering by the [category], [page] and [limit]
     *
     * When [category] is null, ignore category in the filtering
     * returning heads with any category.
     *
     * When [limit] is -1, return all possible heads.
     */
    suspend fun get(category: HeadCategory?, page: Int, limit: Int): List<Head>
}

expect object HeadApi {
    val service: IHeadApiService
}