package br.com.devsrsouza.mcheads.client

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.toByteArray

class KtorHeadApi(private val http: HttpClient) : IHeadApiService {

    override suspend fun search(
        name: String
    ): List<Head> {
        return http.get("${API_URL}/search/${name}")
    }

    override suspend fun searchCategory(
        category: HeadCategory
    ): List<Head> {
        return http.get("${API_URL}/category/${category.name.toLowerCase()}")
    }

    override suspend fun getRender(
        headId: Int
    ): ByteArray? {
        try {
            val response = http.get<HttpResponse>("${API_URL}/image/${headId}")
            if(response.status == HttpStatusCode.OK) {
                return response.content.toByteArray()
            } else return null
        } catch (e: Throwable) {
            return null
        }
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