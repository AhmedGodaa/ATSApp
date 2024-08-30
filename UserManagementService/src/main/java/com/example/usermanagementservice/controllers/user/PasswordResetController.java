package com.example.usermanagementservice.controllers.user;

import com.example.usermanagementservice.models.dto.request.ResetPasswordRequest;
import com.example.usermanagementservice.models.dto.response.StringMessageResponse;
import com.example.usermanagementservice.services.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.auth.url}" + "${api.auth.password.url}")
public class PasswordResetController {

    private final UserService userService;

    public PasswordResetController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to request OOB code
    @PostMapping("${api.auth.password.request.reset.url}")
    public ResponseEntity<StringMessageResponse> requestPasswordReset(@Valid @Email(message = "Invalid email address") @NotBlank(message = "Email is required") @RequestParam String email) {
        userService.initiatePasswordReset(email);
        return ResponseEntity.ok(new StringMessageResponse("Password reset email sent successfully."));
    }

    // Endpoint to reset password using OOB code
    @PostMapping("${api.auth.password.reset.url}")
    public ResponseEntity<StringMessageResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        userService.resetPasswordWithOobCode(request);
        return ResponseEntity.ok(new StringMessageResponse("Password reset successfully."));
    }
}

