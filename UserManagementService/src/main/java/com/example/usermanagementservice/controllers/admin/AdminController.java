package com.example.usermanagementservice.controllers.admin;

import com.example.usermanagementservice.models.User;
import com.example.usermanagementservice.models.dto.response.MessageResponse;
import com.example.usermanagementservice.models.dto.response.StringMessageResponse;
import com.example.usermanagementservice.services.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.admin.url}")
public class AdminController {

    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("${api.admin.getAllUsers.url}")
    public ResponseEntity<MessageResponse<List<User>>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getAllUser(page, size));
    }

    @PutMapping("${api.admin.authorities.add.url}")
    public ResponseEntity<StringMessageResponse> makeUserAdmin(@Valid @RequestParam @NotBlank(message = "Email must not be blank") @Email(message = "Email must be valid") String email) {
        return ResponseEntity.ok(userService.makeUserAdmin(email));
    }

    @DeleteMapping("${api.admin.authorities.remove.url}")
    public ResponseEntity<StringMessageResponse> removeUserAdmin(@Valid @RequestParam @NotBlank(message = "Email must not be blank") @Email(message = "Email must be valid") String email) {
        return ResponseEntity.ok(userService.removeUserAdmin(email));
    }

    @DeleteMapping("${api.admin.user.delete.url}")
    public ResponseEntity<StringMessageResponse> deleteUser(@Valid @RequestParam @NotBlank(message = "Email must not be blank") @Email(message = "Email must be valid") String email) {
        return ResponseEntity.ok(userService.deleteUser(email));
    }

    @GetMapping("${api.admin.user.get.url}")
    public ResponseEntity<MessageResponse<User>> getUser(@Valid @RequestParam @NotBlank(message = "Email must not be blank") @Email(message = "Email must be valid") String email) {
        return ResponseEntity.ok(userService.getUser(email));
    }
}