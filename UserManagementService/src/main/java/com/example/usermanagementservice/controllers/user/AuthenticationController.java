package com.example.usermanagementservice.controllers.user;

import com.example.usermanagementservice.models.dto.request.CreateUserRequest;
import com.example.usermanagementservice.models.dto.request.UserSignInRequest;
import com.example.usermanagementservice.models.dto.response.*;
import com.example.usermanagementservice.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.auth.url}")
public class AuthenticationController {

    private final UserService userService;


    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("${api.auth.signup.url}")
    public ResponseEntity<StringMessageResponse> signUp(@Valid @RequestBody CreateUserRequest userRequest) {
        return ResponseEntity.ok(userService.signup(userRequest));
    }


    @PostMapping("${api.auth.signin.url}")
    public ResponseEntity<MessageResponse<UserSignInResponse>> signIn(@Valid @RequestBody UserSignInRequest userRequest) {
        return ResponseEntity.ok(userService.signIn(userRequest));


    }


    @GetMapping("${api.auth.refresh.token.url}")
    public ResponseEntity<MessageResponse<RefreshTokenResponse>> refreshAccessToken(@RequestParam(name = "refresh-token") String refreshToken) {
        return ResponseEntity.ok(userService.refreshToken(refreshToken));
    }




}
