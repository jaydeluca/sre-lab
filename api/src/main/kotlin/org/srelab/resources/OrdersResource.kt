package org.srelab.resources

import com.codahale.metrics.MetricRegistry
import com.google.inject.Inject
import io.dropwizard.hibernate.UnitOfWork
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import org.srelab.clients.UsersClient
import org.srelab.core.Order
import org.srelab.dao.OrderDao
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

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
    fun getOrders(@QueryParam("id") id: Int?): List<Order?> {

        val span: Span = tracer.spanBuilder("get_orders_from_db").startSpan()
        try {
            span.makeCurrent().use { ss ->
                id?.let {
                    singleOrderCounter.inc()
                    return listOf(orderDao.findById(it))
                }
            }
        } finally {
            span.end()
        }

        usersClient.get("/")
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
