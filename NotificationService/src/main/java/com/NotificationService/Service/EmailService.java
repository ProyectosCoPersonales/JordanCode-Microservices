package com.NotificationService.Service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleMessage(Long orderId) throws MessagingException;
    void sendOrderConfirmation(String toEmail, String subject, String htmlContent) throws MessagingException;
}
