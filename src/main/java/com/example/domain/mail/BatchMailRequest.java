package com.example.domain.mail;

import lombok.Builder;
import lombok.Data;
import org.springframework.batch.core.JobParameters;

import java.util.Map;

@Builder
@Data
public class BatchMailRequest {
    private String user;
    private String description;
    private String jobName;
    private JobParameters jobParameters;
    private Map<String, Object> additionalParams;
}

