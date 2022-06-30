package org.srelab.dao

import io.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
import org.slf4j.LoggerFactory
import org.srelab.core.Order


class OrderDao(sessionFactory: SessionFactory?) : AbstractDAO<Order>(sessionFactory) {

    companion object {
        private val logger = LoggerFactory.getLogger(OrderDao::class.java)
    }

    fun findById(id: Int): Order? {
        return get(id)
    }

    fun new(order: Order): Order {
        return persist(order)
    }

    fun findAll(): List<Order> {
        return list(namedTypedQuery("org.srelab.core.Order.findAll"))
    }

}