package org.srelab.resources

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
class OrdersResource @Inject constructor(
    metricRegistry: MetricRegistry
) {

    private var counter = metricRegistry.counter("order_retrievals")

    @GET
    @Timed
    fun testCounter(@QueryParam("id") id: Long?): Long {
        counter.inc(1)
        return counter.count
    }
}