package com.example.therebalancer.repository

import com.example.therebalancer.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository
import java.io.File
import java.net.URI

@Repository
class CustomerStrategyRepo {


    fun getCustomersMap() = csvReader().open(File(URI(ClassPathResource("/data/customers.csv").url.toString()))) {
        readAllWithHeaderAsSequence()
            .map {
                Customer(
                    customerId = it.getOrDefault("customerId","0").toInt(),
                    email = it.getOrDefault("email","email@email.com"),
                    dateOfBirth = getDateFromString(it.getOrDefault("dateOfBirth","1900-01-01")),
                    riskLevel = it.getOrDefault("riskLevel","0").toInt(),
                    retirementAge = it.getOrDefault("retirementAge","100").toInt()
                )
            }
            .associateBy {
                it.customerId
            }
    }

    fun getStrategiesList() = csvReader().open(File(URI(ClassPathResource("/data/strategy.csv").url.toString()))) {
        readAllWithHeaderAsSequence()
            .map {
                Strategy(
                    strategyId = it.getOrDefault("strategyId","0").toInt(),
                    minRiskLevel= it.getOrDefault("minRiskLevel","0").toInt(),
                    maxRiskLevel= it.getOrDefault("maxRiskLevel","0").toInt(),
                    minYearsToRetirement= it.getOrDefault("minYearsToRetirement","0").toInt(),
                    maxYearsToRetirement= it.getOrDefault("maxYearsToRetirement","0").toInt(),
                    stocksPercentage = it.getOrDefault("stocksPercentage","0").toDouble(),
                    cashPercentage = it.getOrDefault("cashPercentage","0").toDouble(),
                    bondsPercentage = it.getOrDefault("bondsPercentage","0").toDouble()
                )
            }
            .sortedBy { it.maxRiskLevel }
            .toList()
    }



}