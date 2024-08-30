package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * AddMemberToWorkspaceEvent
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-29T20:04:34.785275100+03:00[Africa/Cairo]", comments = "Generator version: 7.8.0")
public class AddMemberToWorkspaceEvent {

  private String workspaceId;

  private String userId;

  private String ownerId;

  public AddMemberToWorkspaceEvent() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AddMemberToWorkspaceEvent(String workspaceId, String userId) {
    this.workspaceId = workspaceId;
    this.userId = userId;
  }

  public AddMemberToWorkspaceEvent workspaceId(String workspaceId) {
    this.workspaceId = workspaceId;
    return this;
  }

  /**
   * Get workspaceId
   * @return workspaceId
   */
  @NotNull 
  @Schema(name = "workspaceId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("workspaceId")
  public String getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(String workspaceId) {
    this.workspaceId = workspaceId;
  }

  public AddMemberToWorkspaceEvent userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   */
  @NotNull 
  @Schema(name = "userId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public AddMemberToWorkspaceEvent ownerId(String ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  /**
   * Get ownerId
   * @return ownerId
   */
  
  @Schema(name = "ownerId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ownerId")
  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddMemberToWorkspaceEvent addMemberToWorkspaceEvent = (AddMemberToWorkspaceEvent) o;
    return Objects.equals(this.workspaceId, addMemberToWorkspaceEvent.workspaceId) &&
        Objects.equals(this.userId, addMemberToWorkspaceEvent.userId) &&
        Objects.equals(this.ownerId, addMemberToWorkspaceEvent.ownerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(workspaceId, userId, ownerId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddMemberToWorkspaceEvent {\n");
    sb.append("    workspaceId: ").append(toIndentedString(workspaceId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

