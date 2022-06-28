package org.srelab.db

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery

interface OrderDao {
    @SqlQuery("select order from orders where id = :id")
    fun findOrderById(@Bind("id") id: Int): String
}