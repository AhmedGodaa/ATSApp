package com.example.usermanagementservice.controllers.workspace;

import com.example.usermanagementservice.models.dto.response.StringMessageResponse;
import com.example.usermanagementservice.models.events.*;
import com.example.usermanagementservice.services.workspace.AsyncWorkspaceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController {
    private final AsyncWorkspaceService asyncWorkspaceService;

    public WorkspaceController(AsyncWorkspaceService asyncWorkspaceService) {
        this.asyncWorkspaceService = asyncWorkspaceService;
    }

    @PostMapping("/create")
    public StringMessageResponse createWorkspace(@Valid @RequestBody CreateWorkspaceEvent event) {
        return asyncWorkspaceService.createWorkspace(event);
    }

    @PutMapping("/update")
    public StringMessageResponse updateWorkspace(@Valid @RequestBody UpdateWorkspaceEvent event) {
        return asyncWorkspaceService.updateWorkspace(event);
    }

    @DeleteMapping("/delete")
    public StringMessageResponse deleteWorkspace(@Valid @RequestBody DeleteWorkspaceEvent event) {
        return asyncWorkspaceService.deleteWorkspace(event);
    }

    @PostMapping("/addMember")
    public StringMessageResponse addMemberToWorkspace(@Valid @RequestBody AddMemberToWorkspaceEvent event) {
        return asyncWorkspaceService.addMemberToWorkspace(event);
    }

    @PostMapping("/removeMember")
    public StringMessageResponse removeMemberFromWorkspace(@Valid @RequestBody RemoveMemberFromWorkspaceEvent event) {
        return asyncWorkspaceService.removeMemberFromWorkspace(event);
    }
}
