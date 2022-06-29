package org.srelab.dao

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.*


interface OrderDao {
    @SqlUpdate("INSERT INTO \"orders\" (\"customer\", \"purchase_date\") VALUES (:customer, :purchaseDate)")
    fun insert(customer: String?, purchaseDate: Date)


    @SqlQuery("select order from orders where id = :id")
    fun findOrderById(@Bind("id") id: Int): String
}