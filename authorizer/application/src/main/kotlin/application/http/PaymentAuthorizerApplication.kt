package application.http

import infrastructure.config.Configuration
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@SpringBootApplication
@ComponentScan(basePackages = ["application.http.controller", "domain.usecase", "domain.gateway"])
//@EnableFeignClients(basePackages = ["infrastructure.data.http.gateway"])
@Import(Configuration::class)
class PaymentAuthorizerApplication

fun main(args: Array<String>) {
	runApplication<PaymentAuthorizerApplication>(*args)
}
