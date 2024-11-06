package infrastructure.data.http.gateway

import infrastructure.data.http.dto.AccountDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import java.math.BigDecimal
import java.util.UUID

@FeignClient(name = "accounts", url = "\${feign.services.account.base-url}")
interface AccountService  {
    @GetMapping("/account/{id}")
    fun getAccount(@PathVariable id: UUID) : AccountDTO

    @PutMapping("/account/{id}/withdraw/{amount}/{wallet}")
    fun withdraw(@PathVariable id: UUID, @PathVariable amount: BigDecimal, @PathVariable wallet: String) : String
}