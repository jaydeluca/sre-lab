package org.srelab.resources

import com.codahale.metrics.health.HealthCheck

class HealthCheckResource : HealthCheck() {
    override fun check(): Result {
        return Result.healthy()
    }
}
