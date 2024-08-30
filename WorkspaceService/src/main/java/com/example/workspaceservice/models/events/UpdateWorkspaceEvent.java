package com.example.workspaceservice.models.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateWorkspaceEvent {
    private String id;
    private String name;
    private String description;
    private String ownerId;
}