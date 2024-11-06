package com.caju.entity

import com.caju.enums.MCCEnum
import java.math.BigDecimal

abstract class WalletEntity(
    val type: MCCEnum,
    val balance: BigDecimal
) {
    fun hasFunds(amount: BigDecimal) : Boolean {
        return balance.minus(amount) >= BigDecimal.ZERO
    }
}