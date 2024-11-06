package application.http.controller

import application.http.controller.dto.TransactionRequestDTO
import application.http.controller.dto.TransactionResponseDTO
import domain.usecase.IFallbackAuthorizationUseCase
import domain.usecase.IMerchantDependentUseCase
import domain.usecase.ISimpleAuthorizationUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authorizer")
class AuthorizerController(
    val simpleAuthorizationUseCase: ISimpleAuthorizationUseCase,
    val fallbackAuthorizationUseCase: IFallbackAuthorizationUseCase,
    val merchantDependentUseCase: IMerchantDependentUseCase
) {

    @PostMapping("/simple")
    fun simple(@RequestBody request: TransactionRequestDTO): ResponseEntity<TransactionResponseDTO> {
        val transactionRequest  = request.toEntity()
        val response            = simpleAuthorizationUseCase.exec(transactionRequest)
        return ResponseEntity.ok( TransactionResponseDTO( response.status.code  ))
    }

    @PostMapping("/fallback")
    fun fallback(@RequestBody request: TransactionRequestDTO): ResponseEntity<TransactionResponseDTO> {
        val transactionRequest  = request.toEntity()
        val response            = fallbackAuthorizationUseCase.exec(transactionRequest)
        return ResponseEntity.ok( TransactionResponseDTO( response.status.code  ))
    }

    @PostMapping("/merchant-dependent")
    fun merchantDependent(@RequestBody request: TransactionRequestDTO): ResponseEntity<TransactionResponseDTO> {
        val transactionRequest  = request.toEntity()
        val response            = merchantDependentUseCase.exec(transactionRequest)
        return ResponseEntity.ok( TransactionResponseDTO( response.status.code  ))
    }

}