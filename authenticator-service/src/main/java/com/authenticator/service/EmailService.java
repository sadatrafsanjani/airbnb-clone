package com.authenticator.service;

public interface EmailService {

    void verifyNotification(String to, long userId);
}
