package com.example.usermanagementservice.models.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private UUID id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, UUID id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }


}