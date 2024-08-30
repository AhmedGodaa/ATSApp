package com.example.workspaceservice.kafkaConsumers;

import com.example.workspaceservice.services.WorkspaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseWorkspaceEventConsumer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final WorkspaceService workspaceService;

    public BaseWorkspaceEventConsumer(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    protected void logEventReceived(String eventType, String event) {
        logger.warn("Received {} event: {}", eventType, event);
    }

    protected void handleEmptyEvent(String event) {
        if (event == null || event.isEmpty()) {
            logger.error("Received an empty or null event.");
            throw new IllegalArgumentException("Event cannot be null or empty");
        }
    }

    protected void logSuccess(String operation, String identifier) {
        logger.info("{} successfully for event ID: {}", operation, identifier);
    }

    protected void logError(String event, Exception e) {
        if (e instanceof JsonProcessingException) {
            logger.error("Failed to deserialize event: {}", event, e);
        } else {
            logger.error("Error processing workspace event", e);
        }
    }
}

