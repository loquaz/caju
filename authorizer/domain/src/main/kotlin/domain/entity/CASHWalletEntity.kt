package com.caju.entity

import com.caju.enums.MCCEnum
import java.math.BigDecimal

class CASHWalletEntity(balance: BigDecimal) : WalletEntity(MCCEnum.CASH, balance)