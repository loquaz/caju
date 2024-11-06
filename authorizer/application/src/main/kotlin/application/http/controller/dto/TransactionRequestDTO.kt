package application.http.controller.dto

import com.caju.entity.AuthorizationRequestEntity
import java.math.BigDecimal
import java.util.*

data class TransactionRequestDTO(
    val account: UUID,
    val totalAmount: BigDecimal,
    val mcc: Int, //TODO tornar enum inicialmente depois um serviço
    val merchant: String
) {
    fun toEntity(): AuthorizationRequestEntity {
        return AuthorizationRequestEntity(
            accountId = this.account,
            totalAmount = this.totalAmount,
            mcc = this.mcc,
            merchant = this.merchant
        )
    }
}
