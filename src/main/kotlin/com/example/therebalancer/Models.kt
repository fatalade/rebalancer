package com.example.therebalancer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate


data class Customer(
    val customerId: Int,
    val email: String,
    val dateOfBirth: LocalDate,
    val riskLevel: Int,
    val retirementAge: Int
)

data class Strategy(
    val strategyId: Int = 0,
    val minRiskLevel: Int = 0,
    val maxRiskLevel: Int = 0,
    val minYearsToRetirement: Int = 0,
    val maxYearsToRetirement: Int = 0,
    val stocksPercentage: Double = 0.0,
    val cashPercentage: Double = 100.0,
    val bondsPercentage: Double = 0.0,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Portfolio(
    @JsonProperty("customerId")
    val customerId: Int,
    @JsonProperty("stocks")
    val stocks: Double,
    @JsonProperty("bonds")
    val bonds: Double,
    @JsonProperty("cash")
    val cash: Double
)