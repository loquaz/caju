package com.caju.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
//@Table(name = "accounts")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @Column(name = "meal_balance")
    val MEALBalance: BigDecimal,
    @Column(name = "food_balance")
    val FOODBalance: BigDecimal,
    @Column(name = "cash_balance")
    val CASHBalance: BigDecimal
)
