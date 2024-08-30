package com.example.usermanagementservice.services.workspace;

import com.example.usermanagementservice.common.Constants;
import com.example.usermanagementservice.kafka.EventSerializer;
import com.example.usermanagementservice.kafka.KafkaFactory;
import com.example.usermanagementservice.models.dto.response.StringMessageResponse;
import com.example.usermanagementservice.models.events.*;
import com.example.usermanagementservice.services.user.UserAuthenticator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncWorkspaceService {
    private final KafkaProducer<String, String> kafkaEventProducer;
    private final EventSerializer eventSerializer;
    private final UserAuthenticator userAuthenticator;

    public AsyncWorkspaceService(EventSerializer eventSerializer, KafkaFactory kafkaFactory, UserAuthenticator userAuthenticator) {
        this.kafkaEventProducer = kafkaFactory.createProducer();
        this.eventSerializer = eventSerializer;
        this.userAuthenticator = userAuthenticator;
    }

    private StringMessageResponse handleWorkspaceEvent(Object event, String topic, String operationType) {
        try {
            String jsonEvent = eventSerializer.serialize(event);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, operationType + "_workspace_key", jsonEvent);
            CompletableFuture<StringMessageResponse> future = new CompletableFuture<>();

            kafkaEventProducer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    future.complete(new StringMessageResponse("Workspace " + operationType + " event sent successfully"));
                } else {
                    future.complete(new StringMessageResponse("Failed to process " + operationType + " workspace event: " + exception.getMessage()));
                }
            });

            return future.get();

        } catch (JsonProcessingException e) {
            return new StringMessageResponse("Failed to serialize event: " + e.getMessage());
        } catch (Exception e) {
            return new StringMessageResponse("Failed to process workspace event due to an internal error: " + e.getMessage());
        }
    }

    public StringMessageResponse createWorkspace(CreateWorkspaceEvent event) {
        Long UserId = userAuthenticator.authenticate().getId();
        event.setOwnerId(String.valueOf(UserId));
        return handleWorkspaceEvent(event, Constants.CREATE_WORKSPACE_TOPIC, "create");
    }

    public StringMessageResponse updateWorkspace(UpdateWorkspaceEvent event) {
        Long UserId = userAuthenticator.authenticate().getId();
        event.setOwnerId(String.valueOf(UserId));
        return handleWorkspaceEvent(event, Constants.UPDATE_WORKSPACE_TOPIC, "update");
    }

    public StringMessageResponse deleteWorkspace(DeleteWorkspaceEvent event) {
        Long UserId = userAuthenticator.authenticate().getId();
        event.setOwnerId(String.valueOf(UserId));
        return handleWorkspaceEvent(event, Constants.DELETE_WORKSPACE_TOPIC, "delete");
    }

    public StringMessageResponse addMemberToWorkspace(AddMemberToWorkspaceEvent event) {
        Long UserId = userAuthenticator.authenticate().getId();
        event.setOwnerId(String.valueOf(UserId));
        return handleWorkspaceEvent(event, Constants.ADD_MEMBER_TOPIC, "add_member");
    }

    public StringMessageResponse removeMemberFromWorkspace(RemoveMemberFromWorkspaceEvent event) {
        Long UserId = userAuthenticator.authenticate().getId();
        event.setOwnerId(String.valueOf(UserId));
        return handleWorkspaceEvent(event, Constants.REMOVE_MEMBER_TOPIC, "remove_member");
    }
}

