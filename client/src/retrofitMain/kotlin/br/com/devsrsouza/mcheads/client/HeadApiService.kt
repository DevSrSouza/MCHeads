package br.com.devsrsouza.mcheads.client

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface HeadApiService : IHeadApiService {

    @GET("search/{name}")
    override suspend fun search(
        @Path("name") name: String
    ): List<Head>

    @GET("category/{category}")
    override suspend fun searchCategory(
        @Path("category") category: HeadCategory
    ): List<Head>

    @GET("image/{head_id}")
    suspend fun getRenderResponse(
        @Path("{head_id}") headId: Int
    ): ResponseBody

    override suspend fun getRender(
        headId: Int
    ): ByteArray? {
        val response = getRenderResponse(headId)
        return if(response.contentType() == MediaType.get("image/png")) {
            response.bytes()
        } else null
    }
}

actual object HeadApi {

    private val contentType = MediaType.get("application/json")
    private val retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    actual val service: IHeadApiService by lazy {
        retrofit.create<HeadApiService>()
    }
}