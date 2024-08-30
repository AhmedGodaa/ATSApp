package com.example.usermanagementservice.controllers;

import com.example.usermanagementservice.models.Workspace;
import com.example.usermanagementservice.services.workspace.SyncWorkspaceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspacesController {

    private final SyncWorkspaceService syncWorkspaceService;

    @Autowired
    public WorkspacesController(SyncWorkspaceService syncWorkspaceService) {
        this.syncWorkspaceService = syncWorkspaceService;
    }

    @GetMapping("/members")
    public List<String> getAllMembersOfWorkspace(
            @Valid @NotBlank(message = "Please Enter The Workspace ID") @RequestParam String workspaceId) {
        return syncWorkspaceService.getAllMembersOfWorkspace(workspaceId);
    }

    @GetMapping("/all")
    public List<Workspace> getAllWorkspaces(
            @Valid @NotNull(message = "Please Enter Page") @RequestParam int page,
            @Valid @NotNull(message = "Please Enter Size") @RequestParam int size) {
        return syncWorkspaceService.getAllWorkspaces(page, size);
    }

    @GetMapping("/getById")
    public Workspace getWorkspaceById(
            @Valid @NotBlank(message = "Please Enter The ID") @RequestParam String id) {
        return syncWorkspaceService.getWorkspaceById(id);
    }
}