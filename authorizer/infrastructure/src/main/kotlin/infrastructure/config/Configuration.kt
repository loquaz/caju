package infrastructure.config

import domain.gateway.AccountGateway
import domain.gateway.MCCByMerchantGateway
import domain.gateway.TransactionGateway
import domain.usecase.*
import infrastructure.data.db.InMemoryMCCByMerchantNameGatewayImpl
import infrastructure.data.http.gateway.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["infrastructure.data.db.repository"])
@ComponentScan(basePackages = ["infrastructure.data.db.repository", "infrastructure.data.http.gateway", "infrastructure.data.db.gateway"])
@EntityScan(basePackages = ["infrastructure.data.db.entity"])
@EnableFeignClients(clients = [AccountService::class])
@ImportAutoConfiguration(FeignAutoConfiguration::class)
@Profile("production")
class Configuration   {

    @Bean
    fun mccByMerchantNameGateway() : MCCByMerchantGateway {
        return InMemoryMCCByMerchantNameGatewayImpl()
    }

    @Bean
    fun simpleAuthorizationUseCase(@Autowired accountGateway: AccountGateway, @Autowired transactionGateway: TransactionGateway) : ISimpleAuthorizationUseCase {
        return SimpleAuthorizationUseCase(accountGateway, transactionGateway)
    }

    @Bean
    fun fallbackAuthorizationUseCase(@Autowired accountGateway: AccountGateway, @Autowired transactionGateway: TransactionGateway) : IFallbackAuthorizationUseCase {
        return domain.usecase.FallbackAuthorizationUseCase(accountGateway, transactionGateway)
    }

    @Bean
    fun merchantDependantUseCase(@Autowired accountGateway: AccountGateway, @Autowired transactionGateway: TransactionGateway) : IMerchantDependentUseCase {
        return MerchantDependentUseCase(accountGateway, transactionGateway, mccByMerchantNameGateway())
    }
}