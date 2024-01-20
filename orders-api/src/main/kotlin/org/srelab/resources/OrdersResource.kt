package org.srelab.resources

import com.codahale.metrics.MetricRegistry
import com.google.inject.Inject
import io.dropwizard.hibernate.UnitOfWork
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Tracer
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.srelab.clients.UsersClient
import org.srelab.core.Order
import org.srelab.dao.OrderDao
import org.srelab.utilities.withSpan
import kotlin.random.Random

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
class OrdersResource @Inject constructor(
    metricRegistry: MetricRegistry,
    private val usersClient: UsersClient,
    private val orderDao: OrderDao,
    private val openTelemetry: OpenTelemetry
) {
    private var singleOrderCounter = metricRegistry.counter("order_retrievals_single")
    private var allOrdersCounter = metricRegistry.counter("order_retrievals_all")
    private var tracer: Tracer = openTelemetry.getTracer("manual-instrumentation", "1.0.0")

    @GET
    @UnitOfWork
    fun getOrders(): List<Order> {
        val userId = Random.nextInt(1, 41)
        val orders = withSpan(tracer, "get_all_orders_from_db") { span ->
            span.setAttribute("user_id", userId.toLong())
            allOrdersCounter.inc()
            orderDao.findAll()
        }

        withSpan(tracer, "call_users") { span ->
            span.setAttribute("user_id", userId.toLong())
            usersClient.get("/", userId)
        }

        return orders
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    fun getOrder(@PathParam("id") id: Int): List<Order?> {
        val userId = Random.nextInt(1, 41)
        val orders = withSpan(tracer, "get_order_from_db") { span ->
            span.setAttribute("user_id", userId.toLong())
            singleOrderCounter.inc()
            listOf(orderDao.findById(id))
        }

        withSpan(tracer, "call_users") { span ->
            span.setAttribute("user_id", userId.toLong())
            usersClient.get("/", userId)
        }

        return orders
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
