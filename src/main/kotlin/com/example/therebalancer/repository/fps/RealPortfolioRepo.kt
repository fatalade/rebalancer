package com.example.therebalancer.repository.fps

import com.example.therebalancer.Portfolio
import com.example.therebalancer.fpsBaseUrl
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import java.net.URI

@Repository("real")
class RealPortfolioRepo : PortfolioRepo {

    private val restTemplate = RestTemplate()
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun getPortfolio(customerId: Int): Portfolio? {
        return try {
            restTemplate.getForObject(URI("$fpsBaseUrl/customers/$customerId"), Portfolio::class.java)
        } catch (ex: Exception) {
            logger.error("Exception encountered - ${ex.message}")
            null
        }
    }

    override fun postPortfolios(portfolios: List<Portfolio>): Boolean {
        try {
            val response = restTemplate.postForEntity(URI("$fpsBaseUrl/execute"), portfolios, Portfolio::class.java)
            return response.statusCode.is2xxSuccessful
        }
        catch (ex: Exception) {
            logger.error("Exception encountered - ${ex.message}")
        }
        return false
    }
}