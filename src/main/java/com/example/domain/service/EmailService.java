package com.example.domain.service;

import com.example.domain.mail.BatchMailRequest;
import org.springframework.stereotype.Service;


@Service
public interface EmailService {
    void sendBasicEmail(String from,String subject, String description, String... to);
    void sendMimeEmail(String from, String subject,String templatePath, Object model, String... to);
    void sendBatchReportEmail(String from, String subject, BatchMailRequest batchMail, String... to);
}
