package infrastructure.data.db.repository

import infrastructure.data.db.entity.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID>