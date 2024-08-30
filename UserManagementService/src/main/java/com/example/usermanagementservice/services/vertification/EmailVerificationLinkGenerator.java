package com.example.usermanagementservice.services.vertification;

import com.example.usermanagementservice.models.VerificationToken;
import com.example.usermanagementservice.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationLinkGenerator implements VerificationLinkGenerator {

    @Value("${app.url}")
    private String appUrl;
    private final VerificationTokenRepository verificationTokenRepository;

    public EmailVerificationLinkGenerator(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public String generateLink(String userEmail) {
        // Generate a unique verification token
        String verificationToken = UUID.randomUUID().toString();

        // Save the token to the database with the associated email (pseudocode)
        verificationTokenRepository.save(new VerificationToken(verificationToken, userEmail, LocalDateTime.now().plusDays(1)));

        // Create a verification link containing the token
        return appUrl + "auth/verify-email?token=" + verificationToken;
    }

    @Override
    public String generatePasswordResetLink(String userEmail) {
        // Generate a unique verification token
        String verificationToken = UUID.randomUUID().toString();

        // Save the token to the database with the associated email (pseudocode)
        verificationTokenRepository.save(new VerificationToken(verificationToken, userEmail, LocalDateTime.now().plusDays(1)));

        // Create a verification link containing the token
        return appUrl + "auth/verify-email?token=" + verificationToken;
    }
}
