package com.example.application.rest;

import com.example.application.api.DeviceApi;
import com.example.application.dto.JobInfoDTO;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.job.AbstractJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DeviceController implements DeviceApi {
    private final ApplicationContext context;
    private final JobLauncher jobLauncher;
    public DeviceController(ApplicationContext context,JobLauncher jobLauncher){
        this.context = context;
        this.jobLauncher = jobLauncher;
    }
    @Override
    public ResponseEntity<JobInfoDTO> run(String beanName) {
        AbstractJob job = (AbstractJob) context.getBean(beanName);
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("launchedAt",new Date())
                .addString("jobInstanceId", "TEST_" + job.getName() + "_" + System.currentTimeMillis())
                .toJobParameters();

        try{
            JobExecution jobExecution = this.jobLauncher.run(job,jobParameters);
            return ResponseEntity.ok(new JobInfoDTO(jobExecution));
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
                 JobParametersInvalidException | JobRestartException e) {
            throw new RuntimeException(e);
        }
    }
}
