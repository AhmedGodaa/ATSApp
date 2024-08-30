package com.example.workspaceservice.models.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateWorkspaceEvent {
    private String id;
    private String name;
    private String description;
    private String ownerId;
}
