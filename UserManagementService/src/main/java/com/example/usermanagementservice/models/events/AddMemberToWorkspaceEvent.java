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
public class AddMemberToWorkspaceEvent {
    @NotBlank(message = "Workspace Id is mandatory")
    private String workspaceId;
    @NotBlank(message = "User Id is mandatory")
    private String userId;
    private String ownerId;
}