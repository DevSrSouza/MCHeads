package br.com.devsrsouza.mcheads.client

import br.com.devsrsouza.mcheads.common.API_URL
import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HeadApiService : IHeadApiService {

    @GET("search/{search}")
    override suspend fun search(
        @Path("search") search: String
    ): List<Head>

    @GET("heads")
    override suspend fun get(
        @Query("category") category: HeadCategory?,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<Head>
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