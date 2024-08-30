package com.example.workspaceservice.kafkaConsumers;

import com.example.workspaceservice.common.Constants;
import com.example.workspaceservice.models.Workspace;
import com.example.workspaceservice.models.events.DeleteWorkspaceEvent;
import com.example.workspaceservice.services.WorkspaceService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DeleteWorkspaceEventConsumer extends BaseWorkspaceEventConsumer {

    public DeleteWorkspaceEventConsumer(WorkspaceService workspaceService) {
        super(workspaceService);
    }

    @KafkaListener(topics = Constants.DELETE_WORKSPACE_TOPIC, groupId = Constants.WORKSPACE_EVENT_CONSUMER_GROUP)
    public void consumeDeleteWorkspaceEvent(String event) {
        logEventReceived("delete workspace", event);
        try {
            handleEmptyEvent(event);
            DeleteWorkspaceEvent workspaceEvent = objectMapper.readValue(event, DeleteWorkspaceEvent.class);
            Workspace currentWorkspace = workspaceService.findById(workspaceEvent.getId());
            if (currentWorkspace.getOwnerId().equals(workspaceEvent.getOwnerId())) {
                workspaceService.deleteWorkspace(workspaceEvent.getId());
                logSuccess("Workspace deleted", workspaceEvent.getId());
            } else {
                logger.error("User is not authorized to delete the workspace");
            }

        } catch (Exception e) {
            logError(event, e);
        }
    }
}
