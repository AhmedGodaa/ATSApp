package com.example.workspaceservice.repositories;

import com.example.workspaceservice.models.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends MongoRepository<Workspace, String> {

}
