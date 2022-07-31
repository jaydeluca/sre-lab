package org.srelab.clients

interface ClientInterface {
    fun post(url: String, body: String): String?
    fun get(url: String): String?
}
