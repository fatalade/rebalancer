package com.example.therebalancer.batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.stereotype.Component

@Component
class JobCompletionNotificationListener : JobExecutionListenerSupport() {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun afterJob(jobExecution: JobExecution) {
        logger.info("JOB COMPLETED")
    }

}