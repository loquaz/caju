package domain.usecase

import com.caju.entity.AccountEntity
import com.caju.entity.AuthorizationRequestEntity
import com.caju.entity.AuthorizationResponseEntity
import com.caju.entity.WalletEntity
import com.caju.enums.TransactionStatusEnum
import com.caju.exception.AccountNotFoundException
import com.caju.exception.NotFoundWalletException
import domain.gateway.TransactionGateway
import java.util.*

class SimpleAuthorizationUseCase(
    val accountGateway: domain.gateway.AccountGateway,
    val transactionGateway: TransactionGateway
) : ISimpleAuthorizationUseCase {
    override fun exec(authorizationRequest: AuthorizationRequestEntity): AuthorizationResponseEntity {

        val account = getAccount( authorizationRequest.accountId )
        val wallet  = getWallet(account, authorizationRequest.mcc)
            ?: throw NotFoundWalletException(authorizationRequest.mcc, "Carteira nao encontrada")

        val transaction = buildTransactionObject( account, authorizationRequest, wallet )

        if( transaction.hasFundsOnWallet() ){
            accountGateway.withdraw( account, wallet, authorizationRequest.totalAmount )
        }else{
            transaction.status = TransactionStatusEnum.REJECTED
        }

        transactionGateway.create( transaction )
        return buildResponse( transaction )
    }

    private fun getAccount(accountId: UUID) : AccountEntity {

        return try {
            accountGateway.getAccountById(accountId) ?: throw AccountNotFoundException("Conta não encontrada")
        } catch (e: Exception) {
            throw Exception("Não foi possível alcançar o serviço de contas", e)
        }

    }

    private fun getWallet(account: AccountEntity, mcc: Int) : WalletEntity? {
        return account.getWalletByMCC( mcc )
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

    private fun buildResponse(transaction: domain.entity.TransactionEntity) : AuthorizationResponseEntity {
        val status = transaction.status ?: TransactionStatusEnum.ERROR
        return AuthorizationResponseEntity(status, transaction)
    }

}