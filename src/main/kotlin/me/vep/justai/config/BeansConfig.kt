package me.vep.justai.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.client.RestTemplate


@Configuration
class BeansConfig {

    @Bean
    fun objectMapper() = ObjectMapper().apply {
        propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        registerKotlinModule()
    }

    @Bean
    fun asyncExecutor() = ThreadPoolTaskExecutor().apply {
        corePoolSize = 5
        maxPoolSize = 10
        setQueueCapacity(500)
        initialize()
    }

    @Bean
    fun restTemplate() = RestTemplate()
}
