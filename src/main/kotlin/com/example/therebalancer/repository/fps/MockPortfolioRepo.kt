package com.example.therebalancer.repository.fps

import com.example.therebalancer.Portfolio
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import kotlin.random.Random

@Repository("mock")
class MockPortfolioRepo : PortfolioRepo {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    val portfolioMap = mutableMapOf<Int,Portfolio>()

    override fun getPortfolio(customerId: Int): Portfolio? {
        if (portfolioMap.containsKey(customerId)){
            // found customer in our hashmap
            return portfolioMap[customerId]
        }
        // else, generate entry and place it in our hashmap
        val item = Portfolio(customerId, Random(customerId).nextInt(0,10000).toDouble(), Random(customerId).nextInt(0,10001).toDouble(), Random(customerId).nextInt(0,10002).toDouble())
        portfolioMap[customerId] = item
        return item
    }

    override fun postPortfolios(portfolios: List<Portfolio>): Boolean {
        // for each new portfolio/trade item, go through the existing DB/portfolio list and execute the trades
        portfolios.forEach { tradeItem ->
            portfolioMap[tradeItem.customerId]?.let { oldPortfolioItem ->
                portfolioMap[tradeItem.customerId] = oldPortfolioItem.copy(stocks = oldPortfolioItem.stocks + tradeItem.stocks, bonds = oldPortfolioItem.bonds + tradeItem.bonds, cash = oldPortfolioItem.cash + tradeItem.cash)
            }
        }
        logger.info("Posted portfolios. Now DB contains ${portfolioMap.values}")
        return true
    }
}