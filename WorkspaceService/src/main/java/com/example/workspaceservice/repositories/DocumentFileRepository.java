package com.example.workspaceservice.repositories;

import com.example.workspaceservice.models.DocumentFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentFileRepository extends MongoRepository<DocumentFile, String> {
    Optional<List<DocumentFile>> findByWorkspaceId(String workspaceId);
}
