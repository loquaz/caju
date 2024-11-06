package com.caju.exception

class NotFoundWalletException(val mccCode: Int, msg: String) : RuntimeException("[${mccCode}] ${msg}")