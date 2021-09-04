package com.rafsan.rentservice.service;

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
    public void sendCredentials(String to, String username, String password) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("MailTrap.io");
        mail.setSubject("Login Credentials");
        mail.setText("Username: " + username + "\n "
                + "Password: " + password);

        mailSender.send(mail);
    }

    @Override
    @Async
    public void verifyEmail(String to){

        String body = "<a href='http://localhost:4200/verify-email' target='_blank'>Verify</a>";

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("MailTrap.io");
        mail.setSubject("Login Credentials");
        mail.setText(body);

        mailSender.send(mail);
    }

    @Override
    @Async
    public void notifyHouseApproval(String to) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("MailTrap.io");
        mail.setSubject("Message from Admin");
        mail.setText("Your house post is approved");

        mailSender.send(mail);
    }

    @Override
    @Async
    public void notifyHouseRejection(String to) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("MailTrap.io");
        mail.setSubject("Message from Admin");
        mail.setText("Your house post is rejected");

        mailSender.send(mail);
    }

    @Override
    @Async
    public void notifyBookingApproval(String to) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("MailTrap.io");
        mail.setSubject("Message from Admin");
        mail.setText("Your booking is rejected");

        mailSender.send(mail);
    }

    @Override
    @Async
    public void notifyBookingRejection(String to) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("MailTrap.io");
        mail.setSubject("Message from Admin");
        mail.setText("Your booking is rejected");

        mailSender.send(mail);
    }

    @Override
    @Async
    public void notifyBookingCancel(String to) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("MailTrap.io");
        mail.setSubject("Message from Admin");
        mail.setText("Your booking is cancelled");

        mailSender.send(mail);
    }
}
