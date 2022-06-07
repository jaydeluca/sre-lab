package org.srelab

import org.srelab.guice.ApplicationModule
import org.srelab.resources.HealthCheckResource
import org.srelab.resources.TelemetryTestResource
import com.authzee.kotlinguice4.getInstance
import com.google.inject.Guice
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment


class Application : Application<ApplicationConfig>() {

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            org.srelab.Application().run(*args)
        }
    }

    override fun initialize(bootstrap: Bootstrap<ApplicationConfig>?) {
        // nothing to do yet
    }

    override fun run(configuration: ApplicationConfig,
                     environment: Environment) {

        val modules = listOf(
            ApplicationModule(environment)
        )

        val injector = Guice.createInjector(modules)
        injector.injectMembers(this)

        environment.jersey().register(injector.getInstance<TelemetryTestResource>())
        environment.healthChecks().register("HealthCheck", HealthCheckResource())
    }
}