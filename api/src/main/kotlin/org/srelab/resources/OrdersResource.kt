package org.srelab.resources

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork
import org.srelab.core.Order
import org.srelab.dao.OrderDao
import javax.ws.rs.*

import javax.ws.rs.core.MediaType;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
class OrdersResource constructor(
    metricRegistry: MetricRegistry,
    private val orderDao: OrderDao
) {

    private var counter = metricRegistry.counter("order_retrievals")

    @GET
    @Timed
    fun testCounter(@QueryParam("id") id: Long?): Long {
        counter.inc(1)
        return counter.count
    }

    @POST
    @UnitOfWork
    @Timed
    fun createOrder(order: Order): Order {
        return orderDao.new(order)
    }
}