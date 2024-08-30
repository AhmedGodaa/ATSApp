package com.example.workspaceservice.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@EnableConfigurationProperties(FirebaseCredential.class)
public class FirebaseInitializer {

    private final FirebaseCredential firebaseCredential;
    private static final Logger logger = LoggerFactory.getLogger(FirebaseInitializer.class);

    @Autowired
    public FirebaseInitializer(FirebaseCredential firebaseCredential) {
        this.firebaseCredential = firebaseCredential;
    }

    @PostConstruct
    public void onStart() {
        logger.info("Initializing Firebase App...");
        try {
            initializeFirebaseApp();
        } catch (IOException e) {
            logger.error("Initializing Firebase App {}", e);
        }
    }

    private void initializeFirebaseApp() throws IOException {
        byte[] credentialBytes = firebaseCredential.toString().getBytes();

        InputStream inputStream = new ByteArrayInputStream(credentialBytes);
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
    }
}