package org.srelab.core

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "orders")
data class Order (
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @JsonProperty("customer")
    val customer: String,

    @JsonProperty("purchase_date")
    @Column(name = "purchase_date")
    @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val purchaseDate: Date,
)