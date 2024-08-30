package com.example.usermanagementservice.models.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSignUpResponse {
    private Long id;
    private String email;
    private String fullName;
    private String address;
    private String city;
    private String phone;
    private String nationID;
    private boolean isEmailVerified = false;
    private boolean isPhoneVerified = false;
    private List<String> authorities = new ArrayList<>();


}
