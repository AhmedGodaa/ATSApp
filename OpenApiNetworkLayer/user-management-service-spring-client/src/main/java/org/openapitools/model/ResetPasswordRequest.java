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
 * ResetPasswordRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-29T20:04:34.785275100+03:00[Africa/Cairo]", comments = "Generator version: 7.8.0")
public class ResetPasswordRequest {

  private String email;

  private String oobCode;

  private String newPassword;

  public ResetPasswordRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ResetPasswordRequest(String email, String oobCode, String newPassword) {
    this.email = email;
    this.oobCode = oobCode;
    this.newPassword = newPassword;
  }

  public ResetPasswordRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @NotNull 
  @Schema(name = "email", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public ResetPasswordRequest oobCode(String oobCode) {
    this.oobCode = oobCode;
    return this;
  }

  /**
   * Get oobCode
   * @return oobCode
   */
  @NotNull 
  @Schema(name = "oobCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("oobCode")
  public String getOobCode() {
    return oobCode;
  }

  public void setOobCode(String oobCode) {
    this.oobCode = oobCode;
  }

  public ResetPasswordRequest newPassword(String newPassword) {
    this.newPassword = newPassword;
    return this;
  }

  /**
   * Get newPassword
   * @return newPassword
   */
  @NotNull @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$") @Size(min = 6, max = 20) 
  @Schema(name = "newPassword", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("newPassword")
  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResetPasswordRequest resetPasswordRequest = (ResetPasswordRequest) o;
    return Objects.equals(this.email, resetPasswordRequest.email) &&
        Objects.equals(this.oobCode, resetPasswordRequest.oobCode) &&
        Objects.equals(this.newPassword, resetPasswordRequest.newPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, oobCode, newPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResetPasswordRequest {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    oobCode: ").append(toIndentedString(oobCode)).append("\n");
    sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n");
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

