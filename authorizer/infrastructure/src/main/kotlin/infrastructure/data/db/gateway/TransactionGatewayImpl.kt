package infrastructure.data.db.gateway

import domain.gateway.TransactionGateway
import infrastructure.data.db.entity.Transaction
import infrastructure.data.db.repository.TransactionRepository
import org.springframework.stereotype.Component

@Component
class TransactionGatewayImpl(
    val transactionRepository: TransactionRepository
) : TransactionGateway {
    override fun create(transactionEntity: domain.entity.TransactionEntity): domain.entity.TransactionEntity {
        val transaction = Transaction(null, transactionEntity.account.id, transactionEntity.totalAmount, transactionEntity.wallet!!.type.name, transactionEntity.status!!.name)
        val saved = transactionRepository.save( transaction )
        return domain.entity.TransactionEntity(
            saved.id,
            transactionEntity.account,
            transactionEntity.totalAmount,
            transactionEntity.wallet,
            transactionEntity.merchant,
            transactionEntity.status
        )
    }
}