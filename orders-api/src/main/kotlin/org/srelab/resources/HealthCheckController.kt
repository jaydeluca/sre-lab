package org.srelab.resources

import com.codahale.metrics.health.HealthCheckRegistry
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Produces(MediaType.APPLICATION_JSON)
@Path("/status")
class HealthCheckController(private val registry: HealthCheckRegistry) {
    @GET
    fun getStatus(): Set<Map.Entry<String, Any>> {
        return registry.runHealthChecks().entries
    }
}
