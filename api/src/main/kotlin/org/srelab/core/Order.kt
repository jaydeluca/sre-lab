package org.srelab.core

import java.util.*

data class Order (
    val id: Int?,
    val customer: String,
    val purchase_date: Date,
)