package com.example.domain.impl;

import com.example.domain.mail.BatchMailRequest;
import com.example.domain.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    private final Configuration configuration;

    public EmailServiceImpl(JavaMailSender mailSender, Configuration configuration) {
        this.mailSender = mailSender;
        this.configuration = configuration;
    }
    @Override
    public void sendBasicEmail(String from, String subject, String description, String... to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(description);
            this.mailSender.send(message);
        } catch (Exception e) {
            log.error("An error has occurred sending the email, error {}",e.getMessage());
        }
    }

    @Override
    public void sendMimeEmail(String from, String subject, String templatePath, Object model, String... to) {
        try {
            Template template = this.configuration.getTemplate(templatePath);
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template,model);

            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");

            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(htmlBody,true);

            this.mailSender.send(mimeMessage);
        } catch (IOException ex){
            log.error("An IO exception has occurred, error: {}",ex.getMessage());
        } catch (Exception ex){
            log.error("An error has occurred sending the email, error {}",ex.getMessage());
        }
    }

    @Override
    public void sendBatchReportEmail(String from, String subject, BatchMailRequest batchMail, String... to) {
        sendMimeEmail(from,subject,"batch-report-email-template.ftl",batchMail,to);
    }
}
