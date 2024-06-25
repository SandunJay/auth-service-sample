package com.sanjay.learncentral.service.serviceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

//    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void send(
            String to,
            String username,
            String templateName,
            String confirmationUrl
    ) {
        if (templateName == "confirm-email" && templateName.isEmpty()) {
            templateName = "confirm-email";
        } else if (templateName == "passwordChange" && templateName.isEmpty()) {
            templateName = "password-change";
        }else if (templateName == "course-enroll" && templateName.isEmpty()) {
            templateName = "course-enroll";
        }else if (templateName == "checkout-notification" && templateName.isEmpty()) {
            templateName = "checkout-notification";
        }else if (templateName == "checkout-remainder" && templateName.isEmpty()) {
            templateName = "checkout-remainder";
        }else if (templateName == "timetable-notification" && templateName.isEmpty()) {
            templateName = "timetable-notification";
        }else if (templateName == "session-enroll" && templateName.isEmpty()) {
            templateName = "session-enroll";
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            Map<String, Object> properties = new HashMap<>();
            properties.put("username", username);
            properties.put("confirmationUrl", confirmationUrl);

            Context context = new Context();
            context.setVariables(properties);

            helper.setFrom("sandunjayawardhana2020@gmail.com");
            helper.setTo(to);
            helper.setSubject("Welcome to LearnCentral");
            String template = templateEngine.process(templateName, context);

            helper.setText(template, true);
            helper.addInline("logo", new ClassPathResource("templates/learncentral.png"), "image/png");

            mailSender.send(mimeMessage);
//            logger.info("Email successfully sent to {} for {} ."+ username , templateName);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
