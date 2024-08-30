package com.example.workspaceservice.repositories;

import com.example.workspaceservice.models.DocumentFile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocumentRepository extends MongoRepository<DocumentFile, String> {
    List<DocumentFile> findByWorkspaceId(String workspaceId);

    void deleteByWorkspaceId(String workspaceId);
}