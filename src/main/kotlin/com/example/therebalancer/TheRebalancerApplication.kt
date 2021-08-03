package com.example.therebalancer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class TheRebalancerApplication

fun main(args: Array<String>) {
    runApplication<TheRebalancerApplication>(*args)
}
