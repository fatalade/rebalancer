package com.example.therebalancer.repository.fps

import com.example.therebalancer.Portfolio

interface PortfolioRepo {

    fun getPortfolio(customerId: Int): Portfolio?

    fun postPortfolios(portfolios: List<Portfolio>): Boolean

}