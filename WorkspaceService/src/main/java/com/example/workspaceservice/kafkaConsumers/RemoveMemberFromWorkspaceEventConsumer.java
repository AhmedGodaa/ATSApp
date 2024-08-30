package com.example.workspaceservice.kafkaConsumers;

import com.example.workspaceservice.common.Constants;
import com.example.workspaceservice.models.Workspace;
import com.example.workspaceservice.models.events.RemoveMemberFromWorkspaceEvent;
import com.example.workspaceservice.services.WorkspaceService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// Specific consumer for Remove Member from Workspace events
@Service
public class RemoveMemberFromWorkspaceEventConsumer extends BaseWorkspaceEventConsumer {

    public RemoveMemberFromWorkspaceEventConsumer(WorkspaceService workspaceService) {
        super(workspaceService);
    }

    @KafkaListener(topics = Constants.REMOVE_MEMBER_TOPIC, groupId = Constants.WORKSPACE_EVENT_CONSUMER_GROUP)
    public void consumeMemberRemovedFromWorkspaceEvent(String event) {
        logEventReceived("remove member from workspace", event);
        try {
            handleEmptyEvent(event);
            RemoveMemberFromWorkspaceEvent memberEvent = objectMapper.readValue(event, RemoveMemberFromWorkspaceEvent.class);

            Workspace currentWorkspace = workspaceService.findById(memberEvent.getWorkspaceId());
            if (currentWorkspace.getOwnerId().equals(memberEvent.getOwnerId())) {
                workspaceService.removeMemberFromWorkspace(memberEvent.getWorkspaceId(), memberEvent.getUserId());
                logSuccess("Member removed from workspace", memberEvent.getWorkspaceId() + "-" + memberEvent.getUserId());
            } else {
                logger.error("User is not authorized to delete user from this workspace");
            }


        } catch (Exception e) {
            logError(event, e);
        }
    }
}
