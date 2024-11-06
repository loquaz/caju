package com.caju.service

import com.caju.controller.dto.AccountDTO
import com.caju.controller.dto.AccountRequestDTO
import com.caju.entity.Account
import com.caju.exception.AccountNotFountException
import com.caju.repository.AccountRepository
import jakarta.transaction.Transactional
import jdk.jfr.DataAmount
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.UUID

@Component
class AccountService(val accountRepository: AccountRepository) {
    fun getAccount(id: UUID) : Account {
        return accountRepository.findById(id).orElseThrow{
            AccountNotFountException("07", "conta [${id}] nao encontrada")
        }
    }

    @Transactional
    fun save(accountRequestDTO: AccountRequestDTO): UUID? {
        val account = Account(id = null,
            CASHBalance = accountRequestDTO.CASHBalance,
            MEALBalance = accountRequestDTO.MEALBalance,
            FOODBalance = accountRequestDTO.FOODBalance)
       return accountRepository.save(account).id
    }

    @Transactional
    fun withdrawFrom(accountId: UUID, amount: BigDecimal, wallet: String)  {
        val account = accountRepository.findById(accountId).get()
        var updatedAccount: Account

        if(wallet.startsWith("meal")) {
            updatedAccount = Account(
                id = accountId,
                MEALBalance = account.MEALBalance.minus(amount),
                FOODBalance = account.FOODBalance,
                CASHBalance = account.CASHBalance)
        }else if(wallet.startsWith("food")){
            updatedAccount = Account(
                id = accountId,
                MEALBalance = account.MEALBalance,
                FOODBalance = account.FOODBalance.minus(amount),
                CASHBalance = account.CASHBalance)
        }else if(wallet.startsWith("cash")){
            updatedAccount = Account(
                id = accountId,
                MEALBalance = account.MEALBalance,
                FOODBalance = account.FOODBalance,
                CASHBalance = account.CASHBalance.minus(amount))
        }
        else
            throw Exception("[${wallet}] nao e um tipo de carteira valido")
        accountRepository.save(updatedAccount)
    }

    fun getAll(): List<AccountDTO>? {
        val accountEntities = accountRepository.findAll()
        val accounts = arrayListOf<AccountDTO>()
        for (acc in accountEntities)
            accounts.add(
                AccountDTO(
                    id = acc.id,
                    MEALBalance = acc.MEALBalance,
                    FOODBalance = acc.FOODBalance,
                    CASHBalance = acc.CASHBalance
                )
            )
        return accounts
    }
}