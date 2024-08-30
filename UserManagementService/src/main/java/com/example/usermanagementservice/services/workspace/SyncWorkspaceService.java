package com.example.usermanagementservice.services.workspace;

import com.example.usermanagementservice.models.Workspace;
import com.example.usermanagementservice.models.dto.response.ErrorClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SyncWorkspaceService {

    private static final Logger log = LoggerFactory.getLogger(SyncWorkspaceService.class);
    private final RestTemplate restTemplate;

    @Value("${workspace-service.base-url}")
    private String baseUrl;

    public SyncWorkspaceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public List<String> getAllMembersOfWorkspace(String workspaceId) {
        String url = baseUrl + "/workspaces" + "/members?workspaceId=" + workspaceId;
        ObjectMapper mapper = new ObjectMapper();

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            log.warn(responseEntity.getBody());
            return mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });
        } catch (HttpClientErrorException e) {
            handleClientErrorException(e);
            return null; // This will never be reached, but it's needed for compilation
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Workspace> getAllWorkspaces(int page, int size) {
        String url = baseUrl + "/workspaces" + "/all?page=" + page + "&size=" + size;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    String.class
            );
            ObjectMapper mapper = new ObjectMapper();
            List<Workspace> subjects = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });

            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("An error occurred while fetching workspaces: " + responseEntity.getStatusCode());
            }
            return subjects;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching workspaces: " + e.getMessage(), e);
        }
    }

    public Workspace getWorkspaceById(String id) {
        String url = baseUrl + "/workspaces" + "/getById?id=" + id;
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            log.warn("Response: {}", responseEntity.getBody());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseEntity.getBody(), Workspace.class);
        } catch (HttpClientErrorException e) {
            handleClientErrorException(e);
            return null; // This will never be reached, but it's needed for compilation
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleClientErrorException(HttpClientErrorException e) {
        if (e.getStatusCode().is4xxClientError()) {
            try {
                // Attempt to map the error response to your ErrorClass
                ErrorClass errorClass = restTemplate.getForObject(e.getResponseBodyAsString(), ErrorClass.class);
                if (errorClass != null && errorClass.getErrors() != null && !errorClass.getErrors().isEmpty()) {
                    throw new RuntimeException("Error occurred while processing the error response: " + errorClass.getErrors());
                }
            } catch (Exception ex) {
                throw new RuntimeException("Error occurred while processing the error response: " + ex.getMessage(), ex);
            }
        } else {
            throw new RuntimeException("Server error occurred: " + e.getMessage(), e);
        }
    }
}