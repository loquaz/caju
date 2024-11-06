package com.caju

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.caju.repository"])
@ComponentScan(basePackages = ["com.caju.controller","com.caju.entity","com.caju.repository","com.caju.entity","com.caju.service"])
@EntityScan(basePackages = ["com.caju.entity"])
@Configuration
class AccountServiceApp

fun main(args: Array<String>) {
    runApplication<AccountServiceApp>(*args)
}