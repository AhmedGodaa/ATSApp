package com.example.workspaceservice.kafkaConsumers;

import com.example.workspaceservice.common.Constants;
import com.example.workspaceservice.mappers.WorkspaceMapper;
import com.example.workspaceservice.models.events.CreateWorkspaceEvent;
import com.example.workspaceservice.services.WorkspaceService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// Specific consumer for Create Workspace events
@Service
public class CreateWorkspaceEventConsumer extends BaseWorkspaceEventConsumer {
    
    public CreateWorkspaceEventConsumer(WorkspaceService workspaceService) {
        super(workspaceService);
    }

    @KafkaListener(topics = Constants.CREATE_WORKSPACE_TOPIC, groupId = Constants.WORKSPACE_EVENT_CONSUMER_GROUP)
    public void consumeCreateWorkspaceEvent(String event) {
        logEventReceived("create workspace", event);
        try {
            handleEmptyEvent(event);
            CreateWorkspaceEvent workspaceEvent = objectMapper.readValue(event, CreateWorkspaceEvent.class);
            workspaceService.createWorkspace(WorkspaceMapper.toWorkspace(workspaceEvent));
            logSuccess("Workspace created", workspaceEvent.getId());
        } catch (Exception e) {
            logError(event, e);
        }
    }
}
