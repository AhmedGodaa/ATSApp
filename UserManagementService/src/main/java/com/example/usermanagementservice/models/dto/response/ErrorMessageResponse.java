package com.example.usermanagementservice.models.dto.response;

import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse {
    private String message;
}
