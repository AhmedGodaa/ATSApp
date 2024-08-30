package com.example.usermanagementservice.models.events;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeleteWorkspaceEvent {
    @NotBlank(message = "Workspace Id is mandatory")
    private String id;
    private String ownerId;
}