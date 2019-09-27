package br.com.devsrsouza.mcheads.client

import br.com.devsrsouza.mcheads.common.API_URL
import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get

class KtorHeadApi(private val http: HttpClient) : IHeadApiService {

    override suspend fun search(
        search: String
    ): List<Head> {
        return http.get("${API_URL}/search/${search}")
    }

    override suspend fun get(
        category: HeadCategory?,
        page: Int,
        limit: Int
    ): List<Head> {

        return http.get(
            "${API_URL}/heads?page=$page&limit=$limit${if(category != null) "&category=${category.name}" else ""}"
        )
    }
}

actual object HeadApi {

    actual val service: IHeadApiService by lazy {
        KtorHeadApi(HttpClient(){
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        })
    }
}