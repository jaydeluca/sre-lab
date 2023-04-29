package org.srelab.dao

import io.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
import org.srelab.core.Order

class OrderDao(private val sessionFactory: SessionFactory) : AbstractDAO<Order>(sessionFactory) {
    fun findById(id: Int): Order? {
        return get(id)
    }
    fun new(order: Order): Order {
        return persist(order)
    }
    fun update(order: Order) {
        val session = sessionFactory.openSession()
        session.beginTransaction()
        session.update(order)
        session.transaction.commit()
    }
    fun findAll(): List<Order> {
        val session = sessionFactory.openSession()
        session.beginTransaction()
        val query = session.createQuery("SELECT o from Order o", Order::class.java)
        session.transaction.commit()
        return query.list()
    }
}
