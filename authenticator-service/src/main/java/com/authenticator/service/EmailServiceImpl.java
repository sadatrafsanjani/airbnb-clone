package com.authenticator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void verifyNotification(String to, long userId){

        String body = "<a href='http://localhost:9001/api/authenticator/authentication/verify/'"+userId+"' target='_blank'>Verify Email</a>";

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("MailTrap.io");
        mail.setSubject("Verify Email");
        mail.setText(body);

        mailSender.send(mail);
    }
}
