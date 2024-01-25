package org.srelab.guice

import dev.misfitlabs.kotlinguice4.KotlinModule
import com.codahale.metrics.MetricRegistry
import io.dropwizard.core.setup.Environment
import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.OpenTelemetry
import org.srelab.ApplicationConfig

class ApplicationModule(
    private val environment: Environment,
    private val configuration: ApplicationConfig
) : KotlinModule() {
    override fun configure() {
        bind<MetricRegistry>().toInstance(environment.metrics())
        bind<Environment>().toInstance(environment)
        bind<ApplicationConfig>().toInstance(configuration)
        bind<OpenTelemetry>().toInstance(GlobalOpenTelemetry.get())
    }
}
