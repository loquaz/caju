package com.caju.entity

import com.caju.enums.MCCEnum
import java.math.BigDecimal

class FOODWalletEntity(balance: BigDecimal) : WalletEntity(MCCEnum.FOOD, balance)