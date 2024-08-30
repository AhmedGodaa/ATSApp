package com.example.usermanagementservice.models.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Full name must not be blank")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "^01[0125][0-9]{8}$", message = "Invalid Egyptian phone number")
    private String phoneNumber;


    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one special character")
    private String password;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    private String email;


    @NotBlank(message = "National ID must not be blank")
    @Pattern(regexp = "^[0-9]{14}$", message = "National ID must be exactly 14 digits")
    private String nationID;

}
