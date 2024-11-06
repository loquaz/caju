package com.caju.exception

class InsuficcientBalanceException(
    val msg: String
) : RuntimeException(msg)