package com.caju.entity

import com.caju.enums.TransactionStatusEnum
import domain.entity.TransactionEntity

data class AuthorizationResponseEntity(
    val status: TransactionStatusEnum,
    val transaction: domain.entity.TransactionEntity
)
