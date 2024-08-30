package com.example.usermanagementservice.services.vertification;

public interface EmailService {
    void sendHTMLEmail(String sender, String recipient, String subject, String content);
}
