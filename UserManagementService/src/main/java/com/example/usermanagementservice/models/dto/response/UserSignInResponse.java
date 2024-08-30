package com.example.usermanagementservice.models.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSignInResponse {
    private Long id;
    private String email;
    private String fullName;
    private String token;
    private String refreshToken;
}
