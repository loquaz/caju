package com.caju.entity

import com.caju.enums.MCCEnum
import java.math.BigDecimal

class MEALWalletEntity(balance: BigDecimal) : WalletEntity(MCCEnum.MEAL, balance)