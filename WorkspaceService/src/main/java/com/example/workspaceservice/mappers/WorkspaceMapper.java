package com.example.workspaceservice.mappers;

import com.example.workspaceservice.models.Workspace;
import com.example.workspaceservice.models.events.CreateWorkspaceEvent;
import com.example.workspaceservice.models.events.UpdateWorkspaceEvent;

public class WorkspaceMapper {

    public static Workspace toWorkspace(CreateWorkspaceEvent event) {
        if (event == null) {
            return null;
        }
        Workspace workspace = new Workspace();
        workspace.setName(event.getName());
        workspace.setDescription(null);
        workspace.setOwnerId(event.getOwnerId());


        return workspace;
    }

    public static CreateWorkspaceEvent toCreateWorkspaceEvent(Workspace workspace) {
        if (workspace == null) {
            return null;
        }
        CreateWorkspaceEvent event = new CreateWorkspaceEvent();
        event.setId(workspace.getId());
        event.setName(workspace.getName());
        event.setDescription(workspace.getDescription());
        event.setOwnerId(workspace.getOwnerId());
        return event;
    }


    public static Workspace toWorkspace(UpdateWorkspaceEvent event) {
        if (event == null) {
            return null;
        }
        Workspace workspace = new Workspace();
        workspace.setId(event.getId());
        workspace.setName(event.getName());
        workspace.setDescription(event.getDescription());
        workspace.setOwnerId(event.getOwnerId());
        return workspace;
    }

    public static UpdateWorkspaceEvent toUpdateWorkspaceEvent(Workspace workspace) {
        if (workspace == null) {
            return null;
        }
        UpdateWorkspaceEvent event = new UpdateWorkspaceEvent();
        event.setId(workspace.getId());
        event.setName(workspace.getName());
        event.setDescription(workspace.getDescription());
        event.setOwnerId(workspace.getOwnerId());
        return event;
    }
}