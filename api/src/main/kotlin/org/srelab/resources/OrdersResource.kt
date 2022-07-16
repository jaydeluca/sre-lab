package org.srelab.resources

import com.codahale.metrics.MetricRegistry
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

    private var singleOrderCounter = metricRegistry.counter("order_retrievals_single")
    private var allOrdersCounter = metricRegistry.counter("order_retrievals_all")

    @GET
    @UnitOfWork
    fun getOrders(@QueryParam("id") id: Int?): List<Order?> {
        id?.let {
            singleOrderCounter.inc()
            return listOf(orderDao.findById(it))
        }
        allOrdersCounter.inc()
        return orderDao.findAll()
    }

    @POST
    @UnitOfWork
    fun createOrder(order: Order): Order {
        return orderDao.new(order)
    }

    @PUT
    @UnitOfWork
    @Path("/{id}")
    fun updateOrder(
        @PathParam("id") id: Int,
        order: Order
    ): Order? {
        orderDao.update(order)
        return orderDao.findById(id)
    }
}