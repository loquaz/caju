package infrastructure.data.http.dto

import java.math.BigDecimal
import java.util.*

data class AccountDTO(
    val id: UUID,
    val cashbalance: BigDecimal,
    val mealbalance: BigDecimal,
    val foodbalance: BigDecimal
)
