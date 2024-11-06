package com.caju.controller

import com.caju.controller.dto.AccountDTO
import com.caju.controller.dto.AccountRequestDTO
import com.caju.entity.Account
import com.caju.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.UUID

@RestController
@RequestMapping("/account")
class AccountController(val accountService: AccountService) {

    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: UUID) : ResponseEntity<Account>{
        val account = accountService.getAccount(id)
        return ResponseEntity.ok(account)
    }

    @GetMapping("/")
    fun getAll() : ResponseEntity<List<AccountDTO>>{
        val accounts = accountService.getAll()
        return ResponseEntity.ok(accounts)
    }

    @PostMapping("/")
    fun create(@RequestBody accountRequest: AccountRequestDTO) : ResponseEntity<UUID>{
        val id = accountService.save(accountRequest)
        return ResponseEntity.ok(id)
    }

    @PutMapping("/{id}/withdraw/{amount}/{wallet}")
    fun withdraw(@PathVariable id: UUID,  @PathVariable amount: BigDecimal, @PathVariable wallet: String) : ResponseEntity<String>{
        accountService.withdrawFrom(id, amount, wallet.lowercase())
        println("str")
        return ResponseEntity.ok("str")
    }

}