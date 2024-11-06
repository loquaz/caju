package com.caju.controller.dto

import java.math.BigDecimal

data class AccountRequestDTO(
    val MEALBalance: BigDecimal,
    val FOODBalance: BigDecimal,
    val CASHBalance: BigDecimal
)
