package com.example.usermanagementservice.models.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MakeUserAdminRequest {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

}
