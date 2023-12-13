package com.example.domain.batch.listener;

import com.example.domain.mail.BatchMailRequest;
import com.example.domain.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.example.application.constants.ApiConstants.BATCH_DEVICE_KEY_1;

@Component
@Slf4j
public class JobCompletionNotificationListener implements JobExecutionListener {
    @Value("${spring.mail.username}")
    private String testMail;
    private final EmailService emailService;

    public JobCompletionNotificationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().put(BATCH_DEVICE_KEY_1,0);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        BatchMailRequest batchMailRequest = BatchMailRequest.builder()
                .user("David")
                .jobName(jobExecution.getJobInstance().getJobName())
                .description("The batch process has finished.")
                .jobParameters(jobExecution.getJobParameters())
                .additionalParams(Collections.singletonMap("Processed Items",jobExecution.getExecutionContext().getInt(BATCH_DEVICE_KEY_1)))
                .build();

//        this.emailService.sendBatchReportEmail(this.testMail,"Batch report",batchMailRequest,this.testMail);
    }
}
