package org.srelab.utilities

import com.codahale.metrics.MetricFilter
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.ScheduledReporter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import com.github.jjagged.metrics.reporting.StatsDReporter
import com.github.jjagged.metrics.reporting.statsd.StatsD
import io.dropwizard.metrics.common.BaseFormattedReporterFactory
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

@JsonTypeName("statsd")
class StatsDReporterFactory : BaseFormattedReporterFactory() {

    companion object {
        private val logger = LoggerFactory.getLogger(StatsDReporterFactory::class.java)
    }

    @JsonProperty
    @NotEmpty
    private val host = "localhost"

    @JsonProperty
    @Range(min = 0, max = 49151)
    private val port = 8125

    @JsonProperty
    @NotNull
    private val prefix = ""

    override fun build(registry: MetricRegistry): ScheduledReporter {
        val statsd = StatsD(host, port)
        val reporter = StatsDReporter
            .forRegistry(registry)
            .prefixedWith(prefix)
            .withTags()
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL)
            .build(statsd)
        logger.info("Registering StatsDReporter $reporter")
        return reporter
    }
}
