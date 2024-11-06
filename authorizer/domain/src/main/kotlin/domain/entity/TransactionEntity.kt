package domain.entity

import com.caju.entity.AccountEntity
import com.caju.entity.WalletEntity
import com.caju.enums.TransactionStatusEnum
import java.math.BigDecimal
import java.util.*

class TransactionEntity(
    val id: UUID?,
    val account: AccountEntity,
    val totalAmount: BigDecimal,
    var wallet: WalletEntity?,
    val merchant: String,
    var status: TransactionStatusEnum?
){
    fun hasFundsOnWallet() : Boolean {
        return wallet?.hasFunds(totalAmount) ?: false
    }
}