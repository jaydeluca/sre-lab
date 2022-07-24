package org.srelab.clients

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class UsersClient private constructor(builder: UsersClient.Builder) : ClientInterface  {

    private val baseUrl: String?

    init {
        this.baseUrl = builder.baseUrl
    }

    class Builder {
        var baseUrl: String? = null
            private set
        fun baseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun build() = UsersClient(this)
    }

    private val jsonMediaType: MediaType = "application/json; charset=utf-8".toMediaType()

    private var client = OkHttpClient()
        .newBuilder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .callTimeout(5, TimeUnit.SECONDS)
        .build()

    override fun post(url: String, body: String): String? {
        val jsonBody: RequestBody = body.toRequestBody(jsonMediaType)
        val request: Request = Request.Builder()
            .url(baseUrl + url)
            .post(jsonBody)
            .build()

        try {
            client.newCall(request).execute().use { response -> return response.body?.string() }
        } catch (e: Exception) {
            throw RuntimeException()
        }
    }

    override fun get(url: String): String? {
        val request: Request = Request.Builder()
            .url(baseUrl + url)
            .get()
            .build()

        try {
            client.newCall(request).execute().use { response -> return response.body?.string() }
        } catch (e: Exception) {
            throw RuntimeException()
        }
    }


}