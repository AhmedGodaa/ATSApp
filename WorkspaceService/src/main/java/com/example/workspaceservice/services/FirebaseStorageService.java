package com.example.workspaceservice.services;

import com.example.workspaceservice.firebase.FirebaseCredential;
import com.example.workspaceservice.models.DocumentFile;
import com.example.workspaceservice.models.DocumentType;
import com.example.workspaceservice.models.dtos.request.CreateDocumentFileRequest;
import com.example.workspaceservice.repositories.DocumentFileRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class FirebaseStorageService {
    Logger logger = Logger.getLogger(FirebaseStorageService.class.getName());


    private final FirebaseCredential firebaseCredential;

    private final DocumentFileRepository documentFileRepository;

    public FirebaseStorageService(FirebaseCredential firebaseCredential, DocumentFileRepository documentFileRepository) {
        this.firebaseCredential = firebaseCredential;
        this.documentFileRepository = documentFileRepository;
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        // Create the directory if it doesn't exist
        File tempFile = new File(fileName);
        File parentDir = tempFile.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();  // Create all necessary directories
        }

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    public String upload(MultipartFile multipartFile, CreateDocumentFileRequest createDocumentFileRequest) {
        try {
            // Create the folder structure and file name
            String workspaceFolder = createDocumentFileRequest.getWorkspaceId();
            String fileExtension = getExtension(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = workspaceFolder + "/" + timestamp + fileExtension;

            // Convert the MultipartFile to a File object
            File file = convertToFile(multipartFile, fileName);

            // Upload the file to Firebase
            String URL = uploadFile(file, fileName);

            // Save document metadata to the database
            DocumentFile documentFile = new DocumentFile();
            documentFile.setName(createDocumentFileRequest.getName());
            documentFile.setDescription(createDocumentFileRequest.getDescription());
            documentFile.setWorkspaceId(createDocumentFileRequest.getWorkspaceId());

            // Set the document type based on the file content type
            String contentType = Objects.requireNonNull(multipartFile.getContentType());
            if (contentType.contains("pdf")) {
                documentFile.setType(DocumentType.PDF);
            } else if (contentType.contains("word")) {
                documentFile.setType(DocumentType.WORD);
            } else if (contentType.contains("excel")) {
                documentFile.setType(DocumentType.EXCEL);
            } else if (contentType.contains("image")) {
                documentFile.setType(DocumentType.IMAGE);
            } else if (contentType.contains("text")) {
                documentFile.setType(DocumentType.TEXT);
            } else {
                documentFile.setType(DocumentType.OTHER);
            }

            // Set additional metadata and save the document
            documentFile.setUrl(URL);
            documentFile.setSize(multipartFile.getSize());
            documentFile.setCreatedBy("User");  // Replace "User" with the actual user ID if needed
            documentFileRepository.save(documentFile);

            // Delete the temporary file
            file.delete();

            return URL;
        } catch (Exception e) {
            throw new RuntimeException("File couldn't be uploaded. Something went wrong: " + e.getMessage());
        }
    }

    private String uploadFile(File file, String fileName) throws IOException {

        BlobId blobId = BlobId.of("atsapp-7d40e.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        byte[] credentialBytes = firebaseCredential.toString().getBytes();
        InputStream inputStream = new ByteArrayInputStream(credentialBytes);

        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/atsapp-7d40e.appspot.com/o/%s?alt=media";

        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }


    public String generateUploadUrl(String fileName) throws IOException {
        BlobId blobId = BlobId.of("atsapp-7d40e.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        byte[] credentialBytes = firebaseCredential.toString().getBytes();
        InputStream inputStream = new ByteArrayInputStream(credentialBytes);
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        URL url = storage.signUrl(blobInfo, 7, TimeUnit.DAYS, Storage.SignUrlOption.httpMethod(HttpMethod.PUT));

        return url.toString();
    }
}