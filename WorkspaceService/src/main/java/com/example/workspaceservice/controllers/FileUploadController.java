package com.example.workspaceservice.controllers;

import com.example.workspaceservice.models.dtos.request.CreateDocumentFileRequest;
import com.example.workspaceservice.services.FirebaseStorageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/upload")
public class FileUploadController {
    Logger logger = Logger.getLogger(FileUploadController.class.getName());

    private final FirebaseStorageService firebaseStorageService;


    public FileUploadController(FirebaseStorageService firebaseStorageService) {
        this.firebaseStorageService = firebaseStorageService;
    }

    @GetMapping("/generate-url")
    public String generateUploadUrl(
            @Valid @NotBlank(message = "Please enter file name") @RequestParam String fileName
    ) {
        try {
            return firebaseStorageService.generateUploadUrl(fileName);
        } catch (IOException e) {
            logger.severe("Error generating upload URL: " + e.getMessage());
            return "Error generating upload URL";
        }
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public String uploadFile(@ModelAttribute @Valid CreateDocumentFileRequest createDocumentFileRequest) {
        logger.warning("Uploading file: " + createDocumentFileRequest.getName());
        return firebaseStorageService.upload(createDocumentFileRequest.getFile(), createDocumentFileRequest);
    }


}