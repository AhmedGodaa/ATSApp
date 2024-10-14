package com.example.usermanagementservice.controllers.admin;

import com.example.usermanagementservice.models.EmailTemplate;
import com.example.usermanagementservice.services.email.EmailTemplateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.version}/email/template")
public class EmailController {

    private final EmailTemplateService emailTemplateService;


    public EmailController(EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    @PostMapping("${api.email.create.url}")
    public ResponseEntity<EmailTemplate> createEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
        EmailTemplate createdEmailTemplate = emailTemplateService.createEmailTemplate(emailTemplate);
        return new ResponseEntity<>(createdEmailTemplate, HttpStatus.CREATED);
    }

    @GetMapping("${api.email.getAll.url}")
    public ResponseEntity<List<EmailTemplate>> getAllEmailTemplates(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<EmailTemplate> emailTemplates = emailTemplateService.getAllEmailTemplates(page, size);
        return new ResponseEntity<>(emailTemplates, HttpStatus.OK);
    }

    @GetMapping("${api.email.getById.url}")
    public ResponseEntity<EmailTemplate> getEmailTemplateById(@NotEmpty(message = "ID must be not empty") @RequestParam String id) {
        Optional<EmailTemplate> emailTemplate = emailTemplateService.getEmailTemplateById(id);
        return emailTemplate.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("${api.email.update.url}")
    public ResponseEntity<EmailTemplate> updateEmailTemplate(@Valid @RequestBody EmailTemplate emailTemplate) {
        EmailTemplate updatedEmailTemplate = emailTemplateService.updateEmailTemplate(emailTemplate);
        return new ResponseEntity<>(updatedEmailTemplate, HttpStatus.OK);
    }

    @DeleteMapping("${api.email.delete.url}")
    public ResponseEntity<String> deleteEmailTemplate(@NotEmpty(message = "ID must be not empty") @RequestParam String id) {
        String message = emailTemplateService.deleteEmailTemplate(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}