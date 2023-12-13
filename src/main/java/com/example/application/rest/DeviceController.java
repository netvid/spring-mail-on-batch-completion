package com.example.application.rest;

import com.example.application.api.DeviceApi;
import com.example.application.dto.JobInfoDTO;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.AbstractJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DeviceController implements DeviceApi {
    private final ApplicationContext context;
    private final JobLauncher jobLauncher;
    public DeviceController(JobLauncher jobLauncher,ApplicationContext context){
        this.jobLauncher = jobLauncher;
        this.context = context;
    }
    @Override
    public ResponseEntity<JobInfoDTO> run(String beanName) {
        AbstractJob job = null;
        try {
            job = (AbstractJob) this.context.getBean(beanName);
        } catch (BeansException ex){
            return ResponseEntity.notFound().build();
        }
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("launchedAt",new Date())
                .addString("jobInstanceId", "TEST_" + job + "_" + System.currentTimeMillis())
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
