package configuration

import domain.gateway.TransactionGateway
import domain.usecase.*
import infrastructure.data.db.InMemoryMCCByMerchantNameGatewayImpl
import mocks.domain.gateway.AccountGatewayImpl
import mocks.domain.gateway.TransactionGatewayImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("test")
class TestConfiguration {

    @Bean
    fun accountGateway(): domain.gateway.AccountGateway {
        return AccountGatewayImpl()
    }

    @Bean
    fun transactionGateway(): TransactionGateway {
        return TransactionGatewayImpl()
    }

    @Bean
    fun simpleAuthorizationUseCase() : ISimpleAuthorizationUseCase {
        return SimpleAuthorizationUseCase( accountGateway(), transactionGateway() )
    }

    @Bean
    fun fallbackAuthorizationUseCase() : IFallbackAuthorizationUseCase {
        return FallbackAuthorizationUseCase(accountGateway(), transactionGateway())
    }

    @Bean
    fun merchantDependentUseCase() : IMerchantDependentUseCase {
        return MerchantDependentUseCase( accountGateway(), transactionGateway(), InMemoryMCCByMerchantNameGatewayImpl() )
    }
}