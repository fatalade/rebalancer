package com.example.therebalancer.controller

import com.example.therebalancer.*
import com.example.therebalancer.service.CustomerStrategyService
import com.example.therebalancer.service.PortfolioService
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class DefaultController(@Qualifier("asyncJob") private val asyncJobLauncher: JobLauncher, @Qualifier("jobLauncher") private val syncJobLauncher: JobLauncher, @Qualifier("rebalancingJob") private val rebalancingJob: Job) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/start-job-async")
    fun startJobAsync() : String {
        logger.info("Job starting...")
        asyncJobLauncher.run(rebalancingJob, JobParametersBuilder().toJobParameters())
        return "Started job! Check logs to see status."
    }

    @GetMapping("/start-job-sync")
    fun startJobSync() : String {
        logger.info("Job starting...")
        syncJobLauncher.run(rebalancingJob, JobParametersBuilder().toJobParameters())
        logger.info("Job Completed!")
        return "Job Completed!"
    }
}