package com.example.workspaceservice.services;

import com.example.workspaceservice.models.DocumentFile;
import com.example.workspaceservice.repositories.DocumentFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentFileService {

    private final DocumentFileRepository documentFileRepository;

    public DocumentFileService(DocumentFileRepository documentFileRepository) {
        this.documentFileRepository = documentFileRepository;
    }

    // Create a new DocumentFile
    public DocumentFile createDocumentFile(DocumentFile DocumentFile) {
        return documentFileRepository.save(DocumentFile);
    }

    // Delete a DocumentFile by its ID
    public void deleteDocumentFile(String DocumentFileId) {
        documentFileRepository.deleteById(DocumentFileId);
    }

    // Update a DocumentFile
    public DocumentFile updateDocumentFile(String DocumentFileId, DocumentFile updatedDocumentFile) {
        return documentFileRepository.findById(DocumentFileId)
                .map(DocumentFile -> {
                    DocumentFile.setName(updatedDocumentFile.getName());
                    DocumentFile.setDescription(updatedDocumentFile.getDescription());
                    DocumentFile.setType(updatedDocumentFile.getType());
                    DocumentFile.setSize(updatedDocumentFile.getSize());
                    DocumentFile.setUrl(updatedDocumentFile.getUrl());
                    return documentFileRepository.save(DocumentFile);
                })
                .orElseThrow(() -> new RuntimeException("DocumentFile not found"));
    }

    // Get all DocumentFiles in a workspace
    public List<DocumentFile> getAllDocumentFilesInWorkspace(String workspaceId) {
        return documentFileRepository.findByWorkspaceId(workspaceId).orElseThrow(() -> new RuntimeException("DocumentFiles not found"));
    }


}