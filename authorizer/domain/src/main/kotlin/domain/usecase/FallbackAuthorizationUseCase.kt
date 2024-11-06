package domain.usecase

import com.caju.entity.AccountEntity
import com.caju.entity.AuthorizationRequestEntity
import com.caju.entity.AuthorizationResponseEntity
import com.caju.entity.WalletEntity
import com.caju.enums.TransactionStatusEnum
import com.caju.exception.AccountNotFoundException
import domain.gateway.TransactionGateway
import java.util.*

class FallbackAuthorizationUseCase(
    val accountGateway: domain.gateway.AccountGateway,
    val transactionGateway: TransactionGateway
) : IFallbackAuthorizationUseCase

 {
    override fun exec(authorizationRequest: AuthorizationRequestEntity): AuthorizationResponseEntity {
        val account = getAccount( authorizationRequest.accountId )
        var wallet  = getWallet(account, authorizationRequest.mcc)

        if( wallet == null ){
            TODO("CRIAR EXCEÇÃO PARA CARTEIRA NULA NESSE PONTO")
        }

        val transaction = buildTransactionObject( account, authorizationRequest, wallet )

        if( transaction.hasFundsOnWallet() ){
            accountGateway.withdraw( account, wallet, authorizationRequest.totalAmount )
        }else{
            wallet = getCASHWallet(account)
            transaction.wallet = wallet

            if ( transaction.hasFundsOnWallet() ){
                accountGateway.withdraw( account, wallet!!, authorizationRequest.totalAmount )
            } else {
                transaction.status = TransactionStatusEnum.REJECTED
            }
        }

        transactionGateway.create( transaction )
        return buildResponse( transaction )
    }

    private fun getAccount(accountId: UUID) : AccountEntity {

        return try {

            val account = accountGateway.getAccountById( accountId )

            if( account == null ){
                throw AccountNotFoundException("Conta não encontrada")
            }else
                account

        } catch (e: Exception){
            throw Exception("Não foi possível alcançar o serviço de contas", e)
        }

    }

    fun getWallet(account: AccountEntity, mcc: Int) : WalletEntity? {
        return account.getWalletByMCC( mcc )
    }
     private fun getCASHWallet(account: AccountEntity) : WalletEntity? {
        return account.getCASHWallet()
     }

    private fun buildTransactionObject(account: AccountEntity, authorizationRequest: AuthorizationRequestEntity, walletEntity: WalletEntity) : domain.entity.TransactionEntity {
        return domain.entity.TransactionEntity(
            id = null,
            account = account,
            totalAmount = authorizationRequest.totalAmount,
            wallet = walletEntity,
            merchant = authorizationRequest.merchant,
            status = TransactionStatusEnum.APPROVED
        )
     }

     fun buildResponse(transaction: domain.entity.TransactionEntity) : AuthorizationResponseEntity {
         val status = transaction.status ?: TransactionStatusEnum.ERROR
         return AuthorizationResponseEntity(status, transaction)
     }
}