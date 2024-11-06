package domain.usecase

import application.controller.TestsUtil
import com.caju.entity.AuthorizationRequestEntity
import com.caju.entity.CASHWalletEntity
import com.caju.entity.FOODWalletEntity
import com.caju.entity.MEALWalletEntity
import configuration.AccountGatewayImpl
import configuration.TransactionGatewayImpl
import infrastructure.data.db.InMemoryMCCByMerchantNameGatewayImpl
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class InMemoryMCCByMerchantNameGatewayImplTest {

    val accountGateway              = AccountGatewayImpl()
    val transactionGateway          = TransactionGatewayImpl()
    val mccByMerchantGateway        = InMemoryMCCByMerchantNameGatewayImpl()
    val merchantDependentUseCase    = MerchantDependentUseCase(accountGateway, transactionGateway, mccByMerchantGateway)

    @Test
    fun `when merchant name is UBER TRIP must use CASH wallet`(){

        val request     = authorizationRequest(55.1, "UBER TRIP")
        val transaction = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<CASHWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is PAG*MERCADO ROCHA must use FOOD wallet`(){
        val request      = authorizationRequest(55.1, "PAG*MERCADO ROCHA")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<FOODWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is PICPAY*HIPER IDEAL must use FOOD wallet`(){
        val request      = authorizationRequest(55.1, "PICPAY*HIPER IDEAL")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<FOODWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is MERCADO BOMPRECO must use FOOD wallet`(){
        val request      = authorizationRequest(55.1, "MERCADO BOMPRECO")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<FOODWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is PANIFICADORA IDEAL must use FOOD wallet`(){
        val request      = authorizationRequest(55.1, "PANIFICADORA IDEAL")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<FOODWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is FRIGORIFICO GALINHAS must use FOOD wallet`(){
        val request      = authorizationRequest(55.1, "FRIGORIFICO GALINHAS")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<FOODWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is AÇOUGUE BOA CARNE must use FOOD wallet`(){
        val request      = authorizationRequest(55.1, "AÇOUGUE BOA CARNE")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<FOODWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is UBER EATS must use MEAL wallet`(){
        val request      = authorizationRequest(55.1, "UBER EATS")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<MEALWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is LANCHONETE MISTO QUENTE must use MEAL wallet`(){
        val request      = authorizationRequest(55.1, "LANCHONETE MISTO QUENTE")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<MEALWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is RESTAURANTE MISTURA FINA must use MEAL wallet`(){
        val request      = authorizationRequest(55.1, "RESTAURANTE MISTURA FINA")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<MEALWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is SOPERIA DELICIA must use MEAL wallet`(){
        val request      = authorizationRequest(55.1, "SOPERIA DELICIA")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<MEALWalletEntity>(transaction.transaction.wallet)
    }

    @Test
    fun `when merchant name is GALETO IFOOD must use MEAL wallet`(){
        val request      = authorizationRequest(55.1, "GALETO IFOOD")
        val transaction  = merchantDependentUseCase.exec( request )

        assertNotNull(transaction)
        assertIs<MEALWalletEntity>(transaction.transaction.wallet)
    }

    private fun authorizationRequest(amount: Double, merchant: String) : AuthorizationRequestEntity {
        return AuthorizationRequestEntity(
            accountId = UUID.fromString(TestsUtil.ACCOUNT_ID),
            totalAmount = BigDecimal.valueOf(amount),
            merchant = merchant,
            mcc = 5555
        )
    }
}