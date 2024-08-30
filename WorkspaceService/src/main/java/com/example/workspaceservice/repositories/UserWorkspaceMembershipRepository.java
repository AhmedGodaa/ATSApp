package com.example.workspaceservice.repositories;

import com.example.workspaceservice.models.UserWorkspaceMembership;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWorkspaceMembershipRepository extends MongoRepository<UserWorkspaceMembership, String> {

    List<UserWorkspaceMembership> findByUserId(String userId);

    void deleteByWorkspaceId(String workspaceId);

    List<UserWorkspaceMembership> findByWorkspaceId(String workspaceId);

    void deleteByUserIdAndWorkspaceId(String userId, String workspaceId);
}