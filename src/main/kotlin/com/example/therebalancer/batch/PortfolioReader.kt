package com.example.therebalancer.batch

import com.example.therebalancer.Portfolio
import com.example.therebalancer.service.CustomerStrategyService
import com.example.therebalancer.service.PortfolioService
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.support.IteratorItemReader
import org.springframework.stereotype.Component

@Component
class PortfolioReader(private val csService: CustomerStrategyService, private val portfolioService: PortfolioService) : ItemReader<Portfolio> {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private var delegate: ItemReader<Int>? = null

    override fun read(): Portfolio? {
        if (delegate == null) {
            logger.info("Creating batch item reader...")
            delegate = IteratorItemReader(csService.getCustomerIdList())
        }
        logger.info("Reading next customer")
        delegate?.read()?.let {
            logger.info("Looking at customer [${csService.getCustomersMap()[it]}]")
            logger.info("The right strategy for this customer is [${csService.getCustomerStrategyMap()[it]}]")
            logger.info("Fetching portfolio for this customer from Portfolio Service...")
            val item = portfolioService.getPortfolio(it)
            logger.info("Fetched $item from Portfolio Service.")
            return item ?: throw Exception()
        }
        return null
    }



}