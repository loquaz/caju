package domain.gateway

interface MCCByMerchantGateway {
    fun getMCC(merchant: String) : Int
}