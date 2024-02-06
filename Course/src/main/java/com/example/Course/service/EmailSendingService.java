package com.example.Course.service;

import com.example.Course.exeption.IncorrectEmailFormatException;
import com.example.Course.exeption.NoPdfFileException;
import com.example.Course.handler.GlobalExceptionHandler;
import com.example.Course.validator.EmailValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSendingService {

    private final JavaMailSender mailSender;
    private final EmailValidator emailValidator;


    public void sendEmail(String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("abadjanovas@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);


        mailSender.send(message);

        log.info("Mail sent successfully.");
    }

    public void sendEmailWithAttachment(String toEmail, String subject, String body) throws IOException, MessagingException, NoPdfFileException, IncorrectEmailFormatException {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("abadjanovas@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, false);
            emailValidator.exceptionIncorrectEmailFormat(toEmail);

            Path attachmentPath = Paths.get("C:\\Users\\Geimantronas\\Downloads\\Course\\invoice.pdf");
            emailValidator.exceptionNoPdfFile(attachmentPath);
            byte[] attachmentBytes = Files.readAllBytes(attachmentPath);
            helper.addAttachment("AttachmentName.pdf", new ByteArrayResource(attachmentBytes));

            mailSender.send(message);

            log.info("Mail with attachment sent successfully.");
    }
}
