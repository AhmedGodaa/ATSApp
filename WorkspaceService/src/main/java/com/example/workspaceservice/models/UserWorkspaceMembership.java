package com.example.workspaceservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "user_workspace_memberships")
public class UserWorkspaceMembership {
    @Id
    private String id;

    // Reference to the User ID
    private String userId;

    // Reference to the Workspace ID
    private String workspaceId;
}