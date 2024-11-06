package com.caju.entity

import com.caju.enums.MCCEnum
import java.util.*

data class AccountEntity(
    val id: UUID,
    val wallets: HashMap<MCCEnum, WalletEntity>
) {

    fun addWallet(key: MCCEnum,  value: WalletEntity){
        wallets.put( key, value )
    }

    fun getWalletByMCC(mcc: Int) : WalletEntity?{
        return if( wallets.size == 3 ) {
            when (mcc) {
                5411, 5412 -> wallets.get(MCCEnum.FOOD)
                5811, 5812 -> wallets.get(MCCEnum.MEAL)
                else -> null
            }
        } else
            null
    }

    fun getCASHWallet() : WalletEntity? {
        return wallets.get(MCCEnum.CASH)
    }

    fun chooseWalletByMerchantName(merchant: String) : WalletEntity? {
        TODO("NAO IMPLEMENTADO")
    }

}
