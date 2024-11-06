package application.http.controller

import application.http.controller.dto.TransactionResponseDTO
import com.caju.enums.TransactionStatusEnum
import com.caju.exception.InsuficcientBalanceException
import com.caju.exception.NotFoundWalletException
import feign.FeignException
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ExceptionAdviceController {



    @ExceptionHandler
    fun handlerException(e: Exception) : ResponseEntity<TransactionResponseDTO> {
        println(e)
        return ResponseEntity.ok(TransactionResponseDTO(TransactionStatusEnum.ERROR.code))
    }

    @ExceptionHandler(InsuficcientBalanceException::class)
    fun handlerInsuficcientBalanceException(e: InsuficcientBalanceException) : ResponseEntity<TransactionResponseDTO> {
        return ResponseEntity.ok(TransactionResponseDTO(TransactionStatusEnum.REJECTED.code))
    }

    @ExceptionHandler(NotFoundWalletException::class)
    fun handlerNotFoundWalletException(e: NotFoundWalletException) : ResponseEntity<TransactionResponseDTO> {
        println(e)
        return ResponseEntity.ok(TransactionResponseDTO(TransactionStatusEnum.ERROR.code))
    }

    @ExceptionHandler(FeignException::class)
    fun handleFeignStatusException(e: FeignException, response: HttpServletResponse) {
        println(e.message)
        response.status = e.status()
    }
}