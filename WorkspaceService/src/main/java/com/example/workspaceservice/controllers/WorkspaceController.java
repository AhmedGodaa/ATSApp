package com.example.workspaceservice.controllers;

import com.example.workspaceservice.models.Workspace;
import com.example.workspaceservice.services.WorkspaceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }


    @GetMapping("/members")
    public ResponseEntity<List<String>> getAllMembersOfWorkspace(
            @Valid @NotBlank(message = "Please Enter The Workspace ID") @RequestParam String workspaceId) {
        return ResponseEntity.ok(workspaceService.getAllMembersOfWorkspace(workspaceId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Workspace>> getAllWorkspaces(
            @Valid @NotNull(message = "Please Enter Page") @RequestParam int page,
            @Valid @NotNull(message = "Please Enter Size") @RequestParam int size) {
        return ResponseEntity.ok(workspaceService.getAllWorkspaces(page, size));
    }

    @GetMapping("/getById")
    public ResponseEntity<Workspace> getWorkspaceById(
            @Valid @NotBlank(message = "Please Enter The ID") @RequestParam String id) {
        return ResponseEntity.ok(workspaceService.getWorkspaceById(id));
    }
}