package com.example.usermanagementservice.services.vertification;

public interface VerificationLinkGenerator {
    String generateLink(String userEmail);
    String generatePasswordResetLink(String userEmail);
}
