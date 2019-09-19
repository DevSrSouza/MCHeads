package br.com.devsrsouza.mcheads.client

import br.com.devsrsouza.mcheads.common.API_URL
import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import org.w3c.xhr.XMLHttpRequest
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FrontEndHeadApi : IHeadApiService {
    override suspend fun search(name: String): List<Head> {
        val response = httpGet<String>("${API_URL}/search/$name")
        return parseHeads(response)
    }

    override suspend fun searchCategory(category: HeadCategory): List<Head> {
        val response = httpGet<String>("${API_URL}/category/${category.name.toLowerCase()}")
        return parseHeads(response)
    }

    private fun parseHeads(json: String): List<Head> {
        return Json.parse(Head.serializer().list, json)
    }

    private suspend inline fun <reified T> httpGet(
        url: String,
        contentType: String = "application/json; charset=utf-8"
    ): T = suspendCoroutine { c ->
        val xhr = XMLHttpRequest()
        xhr.onreadystatechange = { _ -> statusHandler(xhr, c) }
        xhr.open("GET", url, true)
        xhr.setRequestHeader("Content-type", contentType)
        xhr.send()
    }

    private inline fun <reified T> statusHandler(
        xhr: XMLHttpRequest,
        coroutineContext: Continuation<T>
    ) {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if (xhr.status / 100 == 2) {
                coroutineContext.resume(xhr.response as T)
            } else {
                coroutineContext.resumeWithException(RuntimeException("HTTP error: ${xhr.status}"))
            }
        } else {
            null
        }
    }

}

actual object HeadApi {
    actual val service: IHeadApiService by lazy {
        FrontEndHeadApi()
    }
}