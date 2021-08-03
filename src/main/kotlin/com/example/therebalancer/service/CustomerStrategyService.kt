package com.example.therebalancer.service

import com.example.therebalancer.Customer
import com.example.therebalancer.Strategy
import com.example.therebalancer.getYearsToRetirement
import com.example.therebalancer.repository.CustomerStrategyRepo
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class CustomerStrategyService(private val repo: CustomerStrategyRepo) {

    @Bean
    fun getCustomerStrategyMap(): Map<Int, Strategy> {

        val customersMap = repo.getCustomersMap()

        val strategiesList = repo.getStrategiesList()

        val customerStrategies = customersMap.keys.associateWith {
            getStrategyForCustomer(customersMap.getValue(it), strategiesList)
        } // creates a hashmap with customerID as the key, and the right strategy as the value

        return customerStrategies

    }

    @Bean
    fun getCustomersMap() = repo.getCustomersMap()


    @Bean
    fun getCustomerIdList() = repo.getCustomersMap().keys.toList()

    private fun getStrategyForCustomer(customer: Customer, strategies: List<Strategy>) : Strategy {
        var result = Strategy()
        val ytr = getYearsToRetirement(customer.dateOfBirth,customer.retirementAge)

        val binarySearchRiskIndex = strategies.binarySearchBy(customer.riskLevel) { it.maxRiskLevel } //as the strategy list is already sorted by max risk level, we can discard any strategies below the customer's needed risk level in log(n) time using a binary search
        val startIndex = if (binarySearchRiskIndex >= 0) binarySearchRiskIndex else (-binarySearchRiskIndex-1)

        if (binarySearchRiskIndex<0 && startIndex==strategies.size){
            // this means everything in the strategy list was below the needed risklevel, so returning default strategy
            return result
        }
        else {
            for (i in startIndex until strategies.size) {
                if (strategies[i].maxRiskLevel < customer.riskLevel){
                    // break if we're looking at strategies below the needed risklevel, since this means we've looked at all suitable strategies
                    break
                }
                if (strategies[i].minRiskLevel <= customer.riskLevel && ytr in strategies[i].minYearsToRetirement..strategies[i].maxYearsToRetirement){
                    // we've found a matching strategy
                    result = strategies[i]
                    break
                }
            }
            return result
        }
    }

}