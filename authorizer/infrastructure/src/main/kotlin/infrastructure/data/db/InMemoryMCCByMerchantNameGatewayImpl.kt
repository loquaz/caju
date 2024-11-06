package infrastructure.data.db

import domain.gateway.MCCByMerchantGateway

class InMemoryMCCByMerchantNameGatewayImpl : MCCByMerchantGateway {

    override fun getMCC(merchant: String) : Int{

        if( merchant.isEmpty() ) return 0
        val prefixes = listOf<String>("UBER", "PAG", "PAGS", "PICPAY", "C6", "STONE")

        if( startsWith( merchant, prefixes ) ){
            val splitName = Regex("(\\*|\\s+|-)").split( merchant )

            if(splitName.isNotEmpty()) {
                val description = splitName.drop(1)
                return getMCCByMerchantName( description.joinToString(separator = " ") )
            }
        }
        return getMCCByMerchantName( merchant )
    }

    private fun startsWith( value: String, prefixes: List<String>) : Boolean {
        for (prefix in prefixes){
            if(value.startsWith(prefix, ignoreCase = true))
                return true
        }
        return false
    }


    fun getMCCByMerchantName(name: String) : Int {
        val MEALRegex = "restaurante|lanchonete|soperia|ifood|eats".toRegex(setOf(RegexOption.IGNORE_CASE))
        val FOODRegex = "hiper|mercado|padaria|panificadora|mercadinho|açougue|frigorifico".toRegex(setOf(RegexOption.IGNORE_CASE))
        return if(FOODRegex.containsMatchIn(name)) {
            5411
        } else if( MEALRegex.containsMatchIn(name) ) {
            5811
        }else
            0
    }
}