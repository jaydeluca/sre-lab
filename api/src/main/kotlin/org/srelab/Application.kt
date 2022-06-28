package org.srelab

import org.srelab.guice.ApplicationModule
import org.srelab.resources.HealthCheckResource
import org.srelab.resources.OrdersResource
import com.authzee.kotlinguice4.getInstance
import com.google.inject.Guice
import io.dropwizard.Application
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.db.PooledDataSourceFactory
import io.dropwizard.jdbi3.JdbiFactory
import io.dropwizard.migrations.MigrationsBundle
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

    override fun initialize(bootstrap: Bootstrap<ApplicationConfig>) {
        bootstrap.addBundle(migrations)
    }

    private val migrations = object : MigrationsBundle<ApplicationConfig>() {
        override fun getDataSourceFactory(configuration: ApplicationConfig): PooledDataSourceFactory {
            return configuration.getDataSourceFactory()
        }
    }


    override fun run(configuration: ApplicationConfig,
                     environment: Environment) {

        val modules = listOf(
            ApplicationModule(environment)
        )

        val injector = Guice.createInjector(modules)
        injector.injectMembers(this)


        environment.jersey().register(injector.getInstance<OrdersResource>())
        environment.healthChecks().register("HealthCheck", HealthCheckResource())
    }
}