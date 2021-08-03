package com.example.therebalancer.batch

import com.example.therebalancer.Portfolio
import com.example.therebalancer.batchSize
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor


@Configuration
@EnableBatchProcessing
class BatchConfig(val jobBuilderFactory: JobBuilderFactory, val stepBuilderFactory: StepBuilderFactory, val portfolioReader: PortfolioReader, val portfolioProcessor: PortfolioProcessor, val portfolioWriter: PortfolioWriter) {

    @Bean
    fun reader() = portfolioReader

    @Bean
    fun processor() = portfolioProcessor

    @Bean
    fun writer() = portfolioWriter

    @Bean
    fun rebalancingJob(listener: JobCompletionNotificationListener, step: Step): Job {
        return jobBuilderFactory["rebalancingJob"]
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .flow(step)
            .end()
            .build()
    }

    @Bean
    fun rebalancingStep(): Step {
        return stepBuilderFactory["rebalancingStep"]
            .chunk<Portfolio, Portfolio>(batchSize)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .faultTolerant()
            .skipLimit(100)
            .skip(Exception::class.java)
            .build()
    }

    @Bean(name = ["asyncJob"])
    fun asyncJobLauncher(jobRepository: JobRepository): JobLauncher {
        val jobLauncher = SimpleJobLauncher()
        jobLauncher.setTaskExecutor(SimpleAsyncTaskExecutor())
        jobLauncher.setJobRepository(jobRepository)
        return jobLauncher
    }

}