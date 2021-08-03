package com.example.therebalancer.batch

import com.example.therebalancer.Portfolio
import com.example.therebalancer.service.CustomerStrategyService
import com.example.therebalancer.service.PortfolioService
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component


@Component
class PortfolioWriter(private val csService: CustomerStrategyService, private val portfolioService: PortfolioService) :
    ItemWriter<Portfolio> {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    override fun write(portfolios: MutableList<out Portfolio>) {
        logger.info("The trades that are about to submitted to Portfolio Service are...")
        portfolios.forEachIndexed { index, portfolio ->
            logger.info("Trade ${index+1} is [$portfolio]")
        }
        if (portfolioService.postPortfolios(portfolios)) {
            logger.info("Successfully submitted trades.")
        }
        else {
            logger.warn("Error submitting trades.")
        }
    }


}