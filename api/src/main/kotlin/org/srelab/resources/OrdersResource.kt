package org.srelab.resources

import com.codahale.metrics.MetricRegistry
import io.dropwizard.hibernate.UnitOfWork
import org.srelab.clients.UsersClient
import org.srelab.core.Order
import org.srelab.dao.OrderDao
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
class OrdersResource constructor(
    metricRegistry: MetricRegistry,
    private val orderDao: OrderDao
) {

    private var singleOrderCounter = metricRegistry.counter("order_retrievals_single")
    private var allOrdersCounter = metricRegistry.counter("order_retrievals_all")
    private var client = UsersClient.Builder().baseUrl("http://localhost:9996").build()

    @GET
    @UnitOfWork
    fun getOrders(@QueryParam("id") id: Int?): List<Order?> {
        id?.let {
            singleOrderCounter.inc()
            return listOf(orderDao.findById(it))
        }
        client.get("/")
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
