package com.example.workspaceservice.kafkaConsumers;

import com.example.workspaceservice.common.Constants;
import com.example.workspaceservice.models.Workspace;
import com.example.workspaceservice.models.events.AddMemberToWorkspaceEvent;
import com.example.workspaceservice.services.WorkspaceService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AddMemberToWorkspaceEventConsumer extends BaseWorkspaceEventConsumer {

    public AddMemberToWorkspaceEventConsumer(WorkspaceService workspaceService) {
        super(workspaceService);
    }

    @KafkaListener(topics = Constants.ADD_MEMBER_TOPIC, groupId = Constants.WORKSPACE_EVENT_CONSUMER_GROUP)
    public void consumeMemberAddedToWorkspaceEvent(String event) {
        logEventReceived("add member to workspace", event);
        try {
            handleEmptyEvent(event);
            AddMemberToWorkspaceEvent memberEvent = objectMapper.readValue(event, AddMemberToWorkspaceEvent.class);
            Workspace currentWorkspace = workspaceService.findById(memberEvent.getWorkspaceId());
            if (currentWorkspace.getOwnerId().equals(memberEvent.getOwnerId())) {
                workspaceService.addMemberToWorkspace(memberEvent.getWorkspaceId(), memberEvent.getUserId());
                logSuccess("Member added to workspace", memberEvent.getWorkspaceId() + "-" + memberEvent.getUserId());

            } else {
                logger.error("User is not authorized to add user to the workspace");
            }


        } catch (Exception e) {
            logError(event, e);
        }
    }
}
