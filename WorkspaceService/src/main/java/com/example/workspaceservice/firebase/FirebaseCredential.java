package com.example.workspaceservice.firebase;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "firebase")
@Data
@Getter
@Setter
public class FirebaseCredential {

    private String type;
    private String project_id;
    private String private_key_id;
    private String private_key;
    private String client_email;
    private String client_id;
    private String auth_uri;
    private String token_uri;
    private String auth_provider_x509_cert_url;
    private String client_x509_cert_url;

    @Override
    public String toString() {
        return "{\n" +
                "  \"type\": \"" + type + "\",\n" +
                "  \"project_id\": \"" + project_id + "\",\n" +
                "  \"private_key_id\": \"" + private_key_id + "\",\n" +
                "  \"private_key\": \"" + private_key + "\",\n" +
                "  \"client_email\": \"" + client_email + "\",\n" +
                "  \"client_id\": \"" + client_id + "\",\n" +
                "  \"auth_uri\": \"" + auth_uri + "\",\n" +
                "  \"token_uri\": \"" + token_uri + "\",\n" +
                "  \"auth_provider_x509_cert_url\": \"" + auth_provider_x509_cert_url + "\",\n" +
                "  \"client_x509_cert_url\": \"" + client_x509_cert_url + "\"\n" +
                "}";
    }
}