package org.srelab.clients

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.instrumentation.okhttp.v3_0.OkHttpTelemetry
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class UsersClient (
        private val baseUrl: String,
        openTelemetry: OpenTelemetry? = null
) : ClientInterface {

    private val jsonMediaType: MediaType = "application/json; charset=utf-8".toMediaType()

    private val client: OkHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .callTimeout(5, TimeUnit.SECONDS)
            .build()
    init {
        if (openTelemetry != null) {
            OkHttpTelemetry.builder(openTelemetry).build().newCallFactory(client)
        }
    }
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
    override fun get(url: String, userId: Int): String? {
        val request: Request = Request.Builder()
            .header("user_id", userId.toString())
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
