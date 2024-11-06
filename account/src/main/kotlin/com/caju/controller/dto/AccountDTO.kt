package com.caju.controller.dto

import java.math.BigDecimal
import java.util.UUID

data class AccountDTO(
    val id: UUID? = null,
    val MEALBalance: BigDecimal,
    val FOODBalance: BigDecimal,
    val CASHBalance: BigDecimal
)
