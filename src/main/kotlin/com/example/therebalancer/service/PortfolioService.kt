package com.example.therebalancer.service

import com.example.therebalancer.Portfolio
import com.example.therebalancer.repository.fps.PortfolioRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class PortfolioService(@Qualifier("mock") private val repo: PortfolioRepo) {

    fun getPortfolio(customerId: Int) = repo.getPortfolio(customerId)

    fun postPortfolios(portfolios: List<Portfolio>) = repo.postPortfolios(portfolios)

}