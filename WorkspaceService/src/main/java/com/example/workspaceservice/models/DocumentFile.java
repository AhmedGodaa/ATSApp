package com.example.workspaceservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "documents")
public class DocumentFile {

    @Id
    private String id;
    private String name;
    private String description;
    private DocumentType type;
    private long size;
    private String createdBy;
    private String url;
    private String workspaceId;
}