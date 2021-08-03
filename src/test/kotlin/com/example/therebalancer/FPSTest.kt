package com.example.therebalancer

import com.example.therebalancer.controller.DefaultController
import com.example.therebalancer.repository.fps.MockPortfolioRepo
import com.example.therebalancer.repository.fps.PortfolioRepo
import com.example.therebalancer.service.PortfolioService
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class FPSTest {

    private val dataSource = MockPortfolioRepo()
    private val service: PortfolioService = PortfolioService(dataSource)

    private fun setupDB() {
        dataSource.portfolioMap[1] = Portfolio(1, stocks = 5000.0, bonds = 1000.0, cash = 200.0)
        dataSource.portfolioMap[2] = Portfolio(2, stocks = 8000.0, bonds = 100.0, cash = 200.0)
    }

    @Test
    fun `posting trades changes portfolio correctly`(){
        setupDB()

        val trades = listOf(
            Portfolio(1, stocks = -4000.0, bonds = 50.0, cash = 100.0),
            Portfolio(2, stocks = -5000.0, bonds = 1000.0, cash = 0.0)
        )
        service.postPortfolios(trades)

        assert(service.getPortfolio(1) == Portfolio(1, 1000.0,1050.0,300.0))
        assert(service.getPortfolio(2) == Portfolio(2, 3000.0,1100.0,200.0))

    }

}