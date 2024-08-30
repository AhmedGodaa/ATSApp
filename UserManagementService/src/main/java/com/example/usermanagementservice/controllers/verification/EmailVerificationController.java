package com.example.usermanagementservice.controllers.verification;

import com.example.usermanagementservice.services.user.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.auth.url}")
public class EmailVerificationController {

    private final UserService userService; // Service to handle user-related operations

    public EmailVerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("${api.auth.email.verify.url}")
    public ResponseEntity<String> verifyEmail(@NotBlank(message = "Token must be not blank") @RequestParam("token") String token) {
        String htmlContent = userService.verifyUser(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html; charset=UTF-8");
        return new ResponseEntity<>(htmlContent, headers, HttpStatus.OK);
    }
}
