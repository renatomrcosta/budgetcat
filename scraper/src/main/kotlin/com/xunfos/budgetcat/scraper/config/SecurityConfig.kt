package com.xunfos.budgetcat.scraper.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        return http
            .authorizeExchange()
            .pathMatchers(HttpMethod.GET, "/scrape").authenticated()
            .anyExchange().permitAll()
            .and().httpBasic()
            .and().csrf().disable()
            .build()
    }
}
