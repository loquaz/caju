package infrastructure.data.db.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @Column(name="account_id")
    val accountId: UUID,
    val amount: BigDecimal,
    @Column(name="transaction_type")
    val type: String,
    @Column(name="transaction_status")
    val status: String

)
