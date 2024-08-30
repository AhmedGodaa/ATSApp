package com.example.workspaceservice.models.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RemoveMemberFromWorkspaceEvent {
    private String workspaceId;
    private String userId;
    private String ownerId;

}