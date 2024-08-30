package com.example.workspaceservice.kafkaConsumers;

import com.example.workspaceservice.common.Constants;
import com.example.workspaceservice.mappers.WorkspaceMapper;
import com.example.workspaceservice.models.Workspace;
import com.example.workspaceservice.models.events.UpdateWorkspaceEvent;
import com.example.workspaceservice.services.WorkspaceService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// Specific consumer for Update Workspace events
@Service
public class UpdateWorkspaceEventConsumer extends BaseWorkspaceEventConsumer {

    public UpdateWorkspaceEventConsumer(WorkspaceService workspaceService) {
        super(workspaceService);
    }

    @KafkaListener(topics = Constants.UPDATE_WORKSPACE_TOPIC, groupId = Constants.WORKSPACE_EVENT_CONSUMER_GROUP)
    public void consumeUpdateWorkspaceEvent(String event) {
        logEventReceived("update workspace", event);
        try {
            handleEmptyEvent(event);
            UpdateWorkspaceEvent workspaceEvent = objectMapper.readValue(event, UpdateWorkspaceEvent.class);
            Workspace workspace = WorkspaceMapper.toWorkspace(workspaceEvent);
            Workspace currentWorkspace = workspaceService.findById(workspaceEvent.getId());
            if (currentWorkspace.getOwnerId().equals(workspaceEvent.getOwnerId())) {
                workspaceService.updateWorkspace(workspaceEvent.getId(), workspace);
                logSuccess("Workspace updated", workspaceEvent.getId());

            } else {
                logger.error("User is not authorized to update the workspace");
            }

        } catch (Exception e) {
            logError(event, e);
        }
    }
}
