package br.com.devsrsouza.mcheads.js

import br.com.devsrsouza.mcheads.common.Head
import br.com.devsrsouza.mcheads.common.HeadCategory
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import org.w3c.xhr.XMLHttpRequest
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object RequestHead {
    const val API_URL = "http://API-DAS-HEADS.com.br"

    suspend fun search(name: String): List<Head> {
        val response = httpGet("$API_URL/search/$name")
        return parseHeads(response)
    }

    suspend fun allHeadsFromCategory(category: HeadCategory): List<Head> {
        val response = httpGet("$API_URL/category/${category.name.toLowerCase()}")
        return parseHeads(response)
    }

    private fun parseHeads(json: String): List<Head> {
        return Json.parse(Head.serializer().list, json)
    }

    private suspend fun httpGet(url: String): String = suspendCoroutine { c ->
        val xhr = XMLHttpRequest()
        xhr.onreadystatechange = { _ -> statusHandler(xhr, c) }
        xhr.open("GET", url, true)
        xhr.setRequestHeader("Content-type", "application/json; charset=utf-8")
        xhr.send()
    }

    private fun statusHandler(xhr: XMLHttpRequest, coroutineContext: Continuation<String>) {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if (xhr.status / 100 == 2) {
                coroutineContext.resume(xhr.response as String)
            } else {
                coroutineContext.resumeWithException(RuntimeException("HTTP error: ${xhr.status}"))
            }
        } else {
            null
        }
    }
}