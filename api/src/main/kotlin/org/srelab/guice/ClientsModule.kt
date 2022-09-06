package org.srelab.guice

import dev.misfitlabs.kotlinguice4.KotlinModule
import com.google.inject.Provides
import com.google.inject.Singleton
import org.slf4j.LoggerFactory
import org.srelab.ApplicationConfig
import org.srelab.clients.UsersClient

class ClientsModule : KotlinModule() {

    companion object {
        val logger = LoggerFactory.getLogger(ClientsModule::class.java)
    }

    override fun configure() = Unit

    @Provides
    @Singleton
    fun provideUsersClient(
        configuration: ApplicationConfig
    ): UsersClient {
        logger.info("Creating client with base URL: ${configuration.usersClient}")
        return UsersClient.Builder().baseUrl(configuration.usersClient).build()
    }
}
