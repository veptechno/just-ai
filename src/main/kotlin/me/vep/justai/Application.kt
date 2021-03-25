package me.vep.justai

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@PropertySources(
		value = [
			PropertySource("classpath:secret.properties"),
			PropertySource("classpath:application.properties")
		]
)
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
