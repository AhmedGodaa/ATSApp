package com.example.usermanagementservice.services.email;

import com.example.usermanagementservice.models.EmailTemplate;
import com.example.usermanagementservice.repositories.EmailRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailTemplateService {

    private final EmailRepository emailTemplateRepository;

    public EmailTemplateService(EmailRepository emailTemplateRepository) {
        this.emailTemplateRepository = emailTemplateRepository;
    }

    // Create operation
    public EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) {
        // Check if name and content are provided
        if (emailTemplate.getName() == null || emailTemplate.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is mandatory for email template.");
        }
        if (emailTemplate.getContent() == null || emailTemplate.getContent().isEmpty()) {
            throw new IllegalArgumentException("Content is mandatory for email template.");
        }

        return emailTemplateRepository.save(emailTemplate);
    }

    // Read operations
    public List<EmailTemplate> getAllEmailTemplates(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return emailTemplateRepository.findAll(pageable).getContent();
    }

    public Optional<EmailTemplate> getEmailTemplateById(String id) {
        return emailTemplateRepository.findById(Long.parseLong(id));
    }

    // Update operation
    public EmailTemplate updateEmailTemplate(EmailTemplate updatedEmailTemplate) {
        if (updatedEmailTemplate.getId() == null) {
            throw new IllegalArgumentException("Template Id Can't Be Empty");
        }
        Optional<EmailTemplate> optionalEmailTemplate = emailTemplateRepository.findById(updatedEmailTemplate.getId());
        if (optionalEmailTemplate.isPresent()) {
            updatedEmailTemplate.setId(updatedEmailTemplate.getId()); // Ensure the ID remains the same
            return emailTemplateRepository.save(updatedEmailTemplate);
        } else {
            throw new IllegalArgumentException("Email template with id " + updatedEmailTemplate.getId() + " not found.");
        }
    }

    // Delete operation
    public String deleteEmailTemplate(String id) {
        Optional<EmailTemplate> optionalEmailTemplate = emailTemplateRepository.findById(Long.parseLong(id));
        if (optionalEmailTemplate.isPresent()) {
            emailTemplateRepository.deleteById(Long.parseLong(id));
            return "Email Template Deleted Successfully";
        } else {
            throw new IllegalArgumentException("No Email Template found with id: " + id);
        }
    }
}