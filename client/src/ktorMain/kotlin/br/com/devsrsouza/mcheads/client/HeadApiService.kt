package br.com.devsrsouza.mcheads.client

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class KtorHeadApi(private val http: HttpClient) : IHeadApiService {

    override suspend fun search(
        name: String
    ): List<Head> {
        return parseHeadJsonList(http.get("${API_URL}/search/${name}"))
    }

    override suspend fun searchCategory(
        category: HeadCategory
    ): List<Head> {
        return parseHeadJsonList(http.get("${API_URL}/category/${category.name.toLowerCase()}"))
    }

    private fun parseHeadJsonList(json: String): List<Head> {
        return Json.parse(Head.serializer().list, json)
    }

    override suspend fun getRender(
        headId: Int
    ): ByteArray? {
        try {
            return http.get<ByteArray>("${API_URL}/image/${headId}")
        } catch (e: Throwable) {
            return null
        }
    }
}

actual object HeadApi {

    actual val service: IHeadApiService by lazy {
        KtorHeadApi(HttpClient())
    }
}