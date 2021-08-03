package com.example.therebalancer.batch

import com.example.therebalancer.Portfolio
import com.example.therebalancer.service.CustomerStrategyService
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class PortfolioProcessor(private val csService: CustomerStrategyService) : ItemProcessor<Portfolio, Portfolio> {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun process(portfolio: Portfolio): Portfolio {
        logger.info("Generating correct trades for Portfolio [$portfolio]...")
        return generateTradesFromPortfolio(portfolio)
    }

    private fun generateTradesFromPortfolio(portfolio: Portfolio) : Portfolio {
        val total = portfolio.bonds + portfolio.stocks + portfolio.cash
        val appropriateStrategy = csService.getCustomerStrategyMap().getValue(portfolio.customerId)
        val idealPortfolio = portfolio.copy(stocks = total*(appropriateStrategy.stocksPercentage/100.0), bonds = total*(appropriateStrategy.bondsPercentage/100.0), cash = total*(appropriateStrategy.cashPercentage/100.0))
        val trades = idealPortfolio.copy(stocks = idealPortfolio.stocks-portfolio.stocks, bonds = idealPortfolio.bonds-portfolio.bonds, cash = idealPortfolio.cash-portfolio.cash)
        logger.info("Ideal portfolio for Customer ID ${portfolio.customerId} looks like $idealPortfolio. Recommended trades to get there are $trades. Customer's current portfolio is $portfolio and strategy is $appropriateStrategy")
        return trades
    }

}