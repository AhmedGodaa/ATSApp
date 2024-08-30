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
 * DeleteWorkspaceEvent
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-29T20:04:34.785275100+03:00[Africa/Cairo]", comments = "Generator version: 7.8.0")
public class DeleteWorkspaceEvent {

  private String id;

  private String ownerId;

  public DeleteWorkspaceEvent() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DeleteWorkspaceEvent(String id) {
    this.id = id;
  }

  public DeleteWorkspaceEvent id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DeleteWorkspaceEvent ownerId(String ownerId) {
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
    DeleteWorkspaceEvent deleteWorkspaceEvent = (DeleteWorkspaceEvent) o;
    return Objects.equals(this.id, deleteWorkspaceEvent.id) &&
        Objects.equals(this.ownerId, deleteWorkspaceEvent.ownerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ownerId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteWorkspaceEvent {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

