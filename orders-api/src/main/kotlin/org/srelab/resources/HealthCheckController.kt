package org.srelab.resources

import com.codahale.metrics.health.HealthCheckRegistry
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Produces(MediaType.APPLICATION_JSON)
@Path("/status")
class HealthCheckController(registry: HealthCheckRegistry) {
    private val registry: HealthCheckRegistry
    @GET
    fun getStatus(): Set<Map.Entry<String, Any>> {
        return registry.runHealthChecks().entries
    }

    init {
        this.registry = registry
    }
}
