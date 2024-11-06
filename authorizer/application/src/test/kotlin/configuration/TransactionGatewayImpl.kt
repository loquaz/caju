package configuration

import com.caju.enums.TransactionStatusEnum
import domain.gateway.TransactionGateway
import java.util.*

class TransactionGatewayImpl : TransactionGateway {
    override fun create(transactionEntity: domain.entity.TransactionEntity): domain.entity.TransactionEntity {
        return domain.entity.TransactionEntity(
            id = UUID.randomUUID(),
            account = transactionEntity.account,
            totalAmount = transactionEntity.totalAmount,
            merchant = transactionEntity.merchant,
            wallet = transactionEntity.wallet,
            status = TransactionStatusEnum.APPROVED
        )
    }
}