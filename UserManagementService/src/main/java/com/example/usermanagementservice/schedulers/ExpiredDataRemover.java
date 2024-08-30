package com.example.usermanagementservice.schedulers;

import com.example.usermanagementservice.models.PasswordResetToken;
import com.example.usermanagementservice.models.VerificationToken;
import com.example.usermanagementservice.repositories.PasswordResetTokenRepository;
import com.example.usermanagementservice.repositories.UserRepository;
import com.example.usermanagementservice.repositories.VerificationTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredDataRemover {
    PasswordResetTokenRepository passwordResetTokenRepository;
    VerificationTokenRepository verificationTokenRepository;
    UserRepository userRepository;


    // This Schedule annotation will run the removeExpiredData method every day at 5 PM

    @Scheduled(cron = "0 0 17 * * *")
    public void removeExpiredData() {
        removeNotVerifiedUsers();
        removeExpiredPasswordResetTokens();
        removeExpiredVerificationTokens();
    }

    private void removeNotVerifiedUsers() {
        // Implementation of removing not verified users
//        userRepository.findAll().stream()
//                .filter(user -> !user.isVerified())
//                .forEach(user -> userRepository.delete(user));
    }

    private void removeExpiredPasswordResetTokens() {
        // Implementation of removing expired password reset tokens
        passwordResetTokenRepository.findAll().stream()
                .filter(PasswordResetToken::isExpired)
                .forEach(passwordResetToken -> passwordResetTokenRepository.delete(passwordResetToken));

    }

    private void removeExpiredVerificationTokens() {
        verificationTokenRepository.findAll().stream()
                .filter(VerificationToken::isExpired)
                .forEach(verificationToken -> verificationTokenRepository.delete(verificationToken));
    }
}
