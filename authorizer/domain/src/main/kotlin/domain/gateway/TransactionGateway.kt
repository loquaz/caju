package domain.gateway

interface TransactionGateway {
    fun create(transactionEntity: domain.entity.TransactionEntity): domain.entity.TransactionEntity
}