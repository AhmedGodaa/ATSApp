package com.example.workspaceservice.services;

import com.example.workspaceservice.models.UserWorkspaceMembership;
import com.example.workspaceservice.models.Workspace;
import com.example.workspaceservice.repositories.UserWorkspaceMembershipRepository;
import com.example.workspaceservice.repositories.WorkspaceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;

    private final UserWorkspaceMembershipRepository membershipRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository, UserWorkspaceMembershipRepository membershipRepository) {
        this.workspaceRepository = workspaceRepository;
        this.membershipRepository = membershipRepository;
    }

    // Create a new workspace
    public void createWorkspace(Workspace workspace) {
        workspaceRepository.save(workspace);
    }

    // Delete a workspace by its ID
    public void deleteWorkspace(String workspaceId) {
        // Delete all membership relations before deleting the workspace
        membershipRepository.deleteByWorkspaceId(workspaceId);
        workspaceRepository.deleteById(workspaceId);
    }

    public Workspace findById(String id) {
        return workspaceRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Workspace not found with id: " + id)
        );
    }


    // Update a workspace
    public void updateWorkspace(String workspaceId, Workspace updatedWorkspace) {
        workspaceRepository.findById(workspaceId)
                .map(workspace -> {
                    workspace.setName(updatedWorkspace.getName());
                    workspace.setDescription(updatedWorkspace.getDescription());
                    workspace.setOwnerId(updatedWorkspace.getOwnerId());
                    return workspaceRepository.save(workspace);
                })
                .orElseThrow(() -> new RuntimeException("Workspace not found"));
    }


    // Add a member to a workspace
    public void addMemberToWorkspace(String workspaceId, String userId) {
        UserWorkspaceMembership membership = new UserWorkspaceMembership();
        membership.setWorkspaceId(workspaceId);
        membership.setUserId(userId);
        membershipRepository.save(membership);
    }

    // Remove a member from a workspace
    public void removeMemberFromWorkspace(String workspaceId, String userId) {
        membershipRepository.deleteByUserIdAndWorkspaceId(userId, workspaceId);
    }

    // Get all members of a workspace
    public List<String> getAllMembersOfWorkspace(String workspaceId) {
        List<UserWorkspaceMembership> memberships = membershipRepository.findByWorkspaceId(workspaceId);
        return memberships.stream()
                .map(UserWorkspaceMembership::getUserId)
                .collect(Collectors.toList());
    }


    public List<Workspace> getAllWorkspaces(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return workspaceRepository.findAll(pageable).getContent();
    }

    public Workspace getWorkspaceById(String id) {
        return workspaceRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Workspace not found with id: " + id)
        );
    }


}
