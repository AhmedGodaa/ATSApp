package com.example.usermanagementservice.services.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {

    private Long id;
    private String email;
    private String fullName;
    private List<String> authorities = new ArrayList<>();
    private String nationID;
    private String address;
    private String city;
    private String phone;
    private boolean isEmailVerified;
    private boolean isPhoneVerified;
}
