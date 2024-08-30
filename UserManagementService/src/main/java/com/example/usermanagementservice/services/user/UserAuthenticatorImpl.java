package com.example.usermanagementservice.services.user;

import com.example.usermanagementservice.models.User;
import com.example.usermanagementservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticatorImpl implements UserAuthenticator {
    private final UserRepository userRepository;



    public UserAuthenticatorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User not authenticated");
        }
        if (authentication.getName() == null || authentication.getName().isEmpty()) {
            throw new RuntimeException("Error Authenticate User");

        }

        return userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new IllegalStateException("User not found"));
    }
}