package org.srelab.guice

import com.authzee.kotlinguice4.KotlinModule
import com.codahale.metrics.MetricRegistry
import io.dropwizard.setup.Environment

class ApplicationModule(
    private val environment: Environment,
) : KotlinModule() {
    override fun configure() {
        bind<MetricRegistry>().toInstance(environment.metrics())
        bind<Environment>().toInstance(environment)
    }
}
