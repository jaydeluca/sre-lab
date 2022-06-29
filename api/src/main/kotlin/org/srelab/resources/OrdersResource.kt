package org.srelab.resources

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject
import org.srelab.core.Order
import org.srelab.dao.OrderDao
import javax.ws.rs.*

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
class OrdersResource @Inject constructor(
    metricRegistry: MetricRegistry,
    orderDao: OrderDao
) {

    private var counter = metricRegistry.counter("order_retrievals")

    @GET
    @Timed
    fun testCounter(@QueryParam("id") id: Long?): Long {
        counter.inc(1)
        return counter.count
    }

    @POST
    @Timed
    fun createOrder(order: Order) {

    }
}