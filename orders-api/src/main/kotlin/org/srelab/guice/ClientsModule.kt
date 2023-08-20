package org.srelab.guice

import dev.misfitlabs.kotlinguice4.KotlinModule
import com.google.inject.Provides
import com.google.inject.Singleton
import io.opentelemetry.api.OpenTelemetry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.srelab.ApplicationConfig
import org.srelab.clients.UsersClient

class ClientsModule : KotlinModule() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ClientsModule::class.java)
    }

    override fun configure() = Unit

    @Provides
    @Singleton
    fun provideUsersClient(
        configuration: ApplicationConfig,
        openTelemetry: OpenTelemetry
    ): UsersClient {
        logger.info("Creating client with base URL: ${configuration.usersClient}")
        return UsersClient(baseUrl = configuration.usersClient, openTelemetry = openTelemetry)
    }
}
