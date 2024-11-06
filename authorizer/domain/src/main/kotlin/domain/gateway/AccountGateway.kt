package domain.gateway

import com.caju.entity.AccountEntity
import com.caju.entity.WalletEntity
import java.math.BigDecimal
import java.util.*

interface AccountGateway {
    fun getAccountById(id: UUID): AccountEntity?
    fun withdraw(account: AccountEntity, wallet: WalletEntity, totalAmount: BigDecimal)
}