package com.example.usermanagementservice.services.user;

import com.example.usermanagementservice.models.User;

public interface UserAuthenticator {
    User authenticate();
}