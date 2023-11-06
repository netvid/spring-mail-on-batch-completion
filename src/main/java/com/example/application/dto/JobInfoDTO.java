package com.example.application.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;

import java.util.Map;

@Getter
@Setter
public class JobInfoDTO {
    private String jobName;
    private Map<String, JobParameter<?>> parameters;

    public JobInfoDTO(JobExecution jobExecution){
        this.jobName = jobExecution.getJobInstance().getJobName();
        this.parameters = jobExecution.getJobParameters().getParameters();
    }
}
