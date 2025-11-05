package com.github.jonathan5c.login.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendEmail(String to, String subject, String bodyHtml) {
        try {
            MimeMessage mensage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(bodyHtml, true);

            mailSender.send(mensage);
            return true;
        } catch (MailException | MessagingException e) {
            return false;
        }
    }
}
