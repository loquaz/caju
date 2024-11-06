package configuration

import application.controller.TestsUtil
import com.caju.entity.*
import com.caju.enums.MCCEnum
import domain.gateway.AccountGateway
import java.math.BigDecimal
import java.util.*

class AccountGatewayImpl : AccountGateway {
    override fun getAccountById(id: UUID): AccountEntity? {

        val goodId = UUID.fromString(TestsUtil.ACCOUNT_ID)

        return if ( id.equals( goodId ) ){
            val FOODWallet = FOODWalletEntity(BigDecimal(51.0))
            val MEALWallet = MEALWalletEntity(BigDecimal(45.0))
            val CASHWallet = CASHWalletEntity(BigDecimal(60.0))

            AccountEntity(goodId, hashMapOf<MCCEnum, WalletEntity>(
                Pair(MCCEnum.FOOD,FOODWallet),
                Pair(MCCEnum.MEAL,MEALWallet),
                Pair(MCCEnum.CASH,CASHWallet)))

        } else
        null
    }

    override fun withdraw(account: AccountEntity, wallet: WalletEntity, totalAmount: BigDecimal) {}
}