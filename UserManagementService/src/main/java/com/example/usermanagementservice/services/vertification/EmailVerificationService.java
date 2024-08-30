package com.example.usermanagementservice.services.vertification;

import com.example.usermanagementservice.models.EmailTemplate;
import com.example.usermanagementservice.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    @Value("${mail.sender.email}")
    private String senderEmail;


    private final EmailVerificationLinkGenerator verificationLinkGenerator;

    private final EmailService emailService;

    private final EmailRepository emailRepository;

    public EmailVerificationService(EmailVerificationLinkGenerator verificationLinkGenerator, EmailService emailService, EmailRepository emailRepository) {
        this.verificationLinkGenerator = verificationLinkGenerator;
        this.emailService = emailService;
        this.emailRepository = emailRepository;
    }

    public void sendEmailVerificationLink(String email, String username) {
        String verificationLink = verificationLinkGenerator.generateLink(email);
        String html = setupVerificationEmailTemplate(username, verificationLink);
        emailService.sendHTMLEmail(senderEmail, email, "Verify Your Email", html);
    }

    public void sendResetPasswordVerificationLink(String email, String oobCode, String fullName) {
        String html = setupResetPasswordTemplate(oobCode, fullName);
        emailService.sendHTMLEmail(senderEmail, email, "Password Reset Code", html);
    }


    private String setupVerificationEmailTemplate(String username, String actionLink) {
        EmailTemplate emailTemplate = emailRepository.findByName("verification_template").orElseThrow(() -> new RuntimeException("Error Get Mail Template"));
        return emailTemplate.getContent().replace("#USERNAME", username).replace("#ACTIONLINK", actionLink);
    }


    private String setupResetPasswordTemplate(String oobCode, String fullName) {
        EmailTemplate emailTemplate = emailRepository.findByName("password_reset_template").orElseThrow(() -> new RuntimeException("Error Get Mail Template"));
        return emailTemplate.getContent().replace("#RESET_CODE", oobCode).replace("#USERNAME", fullName);
    }
}
