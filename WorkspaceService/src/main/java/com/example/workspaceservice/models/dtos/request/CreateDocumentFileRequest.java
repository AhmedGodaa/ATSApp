package com.example.workspaceservice.models.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDocumentFileRequest {
    private String name;
    private String description;
    private String workspaceId;
    private MultipartFile file;
}
