package com.caju.entity

import java.math.BigDecimal
import java.util.*

//TODO modelar entidade de autorização
data class AuthorizationRequestEntity(
    val accountId: UUID,
    val totalAmount: BigDecimal,
    val merchant: String,
    val mcc: Int
)
