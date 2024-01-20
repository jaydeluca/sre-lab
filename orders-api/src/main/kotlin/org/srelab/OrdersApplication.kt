package org.srelab

import com.google.inject.Guice
import io.dropwizard.core.Application
import io.dropwizard.core.setup.Bootstrap
import io.dropwizard.core.setup.Environment
import io.dropwizard.db.PooledDataSourceFactory
import io.dropwizard.hibernate.HibernateBundle
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper
import io.dropwizard.migrations.MigrationsBundle
import org.srelab.core.Order
import org.srelab.guice.ApplicationModule
import org.srelab.guice.ClientsModule
import org.srelab.guice.DaoModule
import org.srelab.resources.BasicHealthCheck
import org.srelab.resources.HealthCheckController
import org.srelab.resources.OrdersResource
import java.text.SimpleDateFormat

class OrdersApplication : Application<ApplicationConfig>() {
    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            org.srelab.OrdersApplication().run(*args)
        }
    }

    override fun initialize(bootstrap: Bootstrap<ApplicationConfig>) {
        bootstrap.addBundle(migrations)
        bootstrap.addBundle(hibernate)

        val objectMapper = bootstrap.objectMapper
        objectMapper.dateFormat = SimpleDateFormat("yyyy-MM-dd")
    }

    private val migrations = object : MigrationsBundle<ApplicationConfig>() {
        override fun getDataSourceFactory(configuration: ApplicationConfig): PooledDataSourceFactory {
            return configuration.getDataSourceFactory()
        }
    }

    private val hibernate: HibernateBundle<ApplicationConfig?> = object : HibernateBundle<ApplicationConfig?>(
        Order::class.java
    ) {
        override fun getDataSourceFactory(configuration: ApplicationConfig?): PooledDataSourceFactory {
            return configuration!!.getDataSourceFactory()
        }
    }

    override fun run(
        configuration: ApplicationConfig,
        environment: Environment
    ) {
        val modules = listOf(
            ApplicationModule(environment, configuration),
            ClientsModule(),
            DaoModule(hibernate.sessionFactory)
        )

        val injector = Guice.createInjector(modules)
        injector.injectMembers(this)

        // Register Resources
        environment.jersey().register(injector.getInstance(OrdersResource::class.java))

        // Other
        environment.jersey().register(JsonProcessingExceptionMapper(true))
        environment.jersey().register(HealthCheckController(environment.healthChecks()))
        environment.healthChecks().register("HealthCheck", BasicHealthCheck())
    }
}
