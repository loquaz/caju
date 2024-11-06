package com.caju.controller

import com.caju.controller.dto.ErrorDTO
import com.caju.exception.AccountNotFountException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionAdviceController {

    @ExceptionHandler
    fun handlerException(e: Exception) : ResponseEntity<ErrorDTO> {
        println(e)
        return ResponseEntity.ok(ErrorDTO("08", e.message ?: "erro desconhecido"))
    }

    @ExceptionHandler
    fun handlerAccountNotFountException(e: AccountNotFountException) : ResponseEntity<ErrorDTO> {
        return ResponseEntity.ok(ErrorDTO(e.code, e.msg))
    }
}