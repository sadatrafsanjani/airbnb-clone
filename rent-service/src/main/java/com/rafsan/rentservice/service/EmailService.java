package com.rafsan.rentservice.service;

public interface EmailService {

    void sendCredentials(String to, String username, String password);
    void verifyEmail(String to);
    void notifyHouseApproval(String to);
    void notifyHouseRejection(String to);
    void notifyBookingApproval(String to);
    void notifyBookingRejection(String to);
    void notifyBookingCancel(String to);
}
