package com.example.therebalancer

import com.example.therebalancer.controller.DefaultController
import com.example.therebalancer.repository.fps.MockPortfolioRepo
import com.example.therebalancer.service.PortfolioService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest (@Autowired val mockMvc: MockMvc) {

    val baseUrl = "/api/start-job-sync"

    @DisplayName("GET /api/start-job-sync")
    @Test
    fun `start job synchronously`() {
        val res =  mockMvc.get(baseUrl)
                        .andDo { print() }
                        .andExpect {
                            status { isOk() }
                        }
                        .andReturn()
        assert(res.response.contentAsString =="Job Completed!")
    }

}
