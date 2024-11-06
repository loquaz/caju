package com.caju.exception

class AccountNotFountException(val code: String, val msg: String) : RuntimeException(msg)