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
 * RefreshTokenResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-29T20:04:34.785275100+03:00[Africa/Cairo]", comments = "Generator version: 7.8.0")
public class RefreshTokenResponse {

  private String newJwt;

  private String newRefreshToken;

  public RefreshTokenResponse newJwt(String newJwt) {
    this.newJwt = newJwt;
    return this;
  }

  /**
   * Get newJwt
   * @return newJwt
   */
  
  @Schema(name = "newJwt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("newJwt")
  public String getNewJwt() {
    return newJwt;
  }

  public void setNewJwt(String newJwt) {
    this.newJwt = newJwt;
  }

  public RefreshTokenResponse newRefreshToken(String newRefreshToken) {
    this.newRefreshToken = newRefreshToken;
    return this;
  }

  /**
   * Get newRefreshToken
   * @return newRefreshToken
   */
  
  @Schema(name = "newRefreshToken", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("newRefreshToken")
  public String getNewRefreshToken() {
    return newRefreshToken;
  }

  public void setNewRefreshToken(String newRefreshToken) {
    this.newRefreshToken = newRefreshToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RefreshTokenResponse refreshTokenResponse = (RefreshTokenResponse) o;
    return Objects.equals(this.newJwt, refreshTokenResponse.newJwt) &&
        Objects.equals(this.newRefreshToken, refreshTokenResponse.newRefreshToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(newJwt, newRefreshToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RefreshTokenResponse {\n");
    sb.append("    newJwt: ").append(toIndentedString(newJwt)).append("\n");
    sb.append("    newRefreshToken: ").append(toIndentedString(newRefreshToken)).append("\n");
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

