package infrastructure.data.http.gateway

import com.caju.entity.*
import com.caju.enums.MCCEnum
import domain.gateway.AccountGateway
import feign.FeignException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class AccountGatewayImpl(
    @Value("\${feign.services.account.base-url}")
    val url: String,
    val accountClient: AccountService
) : AccountGateway {
    override fun getAccountById(id: UUID): AccountEntity? {
        try {
            val account = accountClient.getAccount(id)
            val cash = CASHWalletEntity(account.cashbalance)
            val meal = MEALWalletEntity(account.mealbalance)
            val food = FOODWalletEntity(account.foodbalance)
            return AccountEntity(
                id = account.id,
                wallets = hashMapOf(
                    Pair(MCCEnum.CASH,cash),
                    Pair(MCCEnum.MEAL,meal),
                    Pair(MCCEnum.FOOD,food))
                )
        }catch (e: FeignException){
            println(url)
            println("Feign ${e}, ${e.responseBody()}")
        }catch (e: Exception){
            println("Geral ${e}")
        }
        return null
    }

    override fun withdraw(account: AccountEntity, wallet: WalletEntity, totalAmount: BigDecimal) {
        println("${account.id}, ${totalAmount},${wallet.type.name}")
        val id = accountClient.withdraw(account.id, totalAmount,wallet.type.name)

    }
}