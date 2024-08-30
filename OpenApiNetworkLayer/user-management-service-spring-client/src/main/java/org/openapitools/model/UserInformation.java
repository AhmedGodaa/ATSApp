package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * UserInformation
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-29T20:04:34.785275100+03:00[Africa/Cairo]", comments = "Generator version: 7.8.0")
public class UserInformation {

  private Long id;

  private String email;

  private String fullName;

  @Valid
  private List<String> authorities = new ArrayList<>();

  private String nationID;

  private String address;

  private String city;

  private String phone;

  private Boolean phoneVerified;

  private Boolean emailVerified;

  public UserInformation id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserInformation email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  
  @Schema(name = "email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserInformation fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Get fullName
   * @return fullName
   */
  
  @Schema(name = "fullName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fullName")
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public UserInformation authorities(List<String> authorities) {
    this.authorities = authorities;
    return this;
  }

  public UserInformation addAuthoritiesItem(String authoritiesItem) {
    if (this.authorities == null) {
      this.authorities = new ArrayList<>();
    }
    this.authorities.add(authoritiesItem);
    return this;
  }

  /**
   * Get authorities
   * @return authorities
   */
  
  @Schema(name = "authorities", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("authorities")
  public List<String> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<String> authorities) {
    this.authorities = authorities;
  }

  public UserInformation nationID(String nationID) {
    this.nationID = nationID;
    return this;
  }

  /**
   * Get nationID
   * @return nationID
   */
  
  @Schema(name = "nationID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nationID")
  public String getNationID() {
    return nationID;
  }

  public void setNationID(String nationID) {
    this.nationID = nationID;
  }

  public UserInformation address(String address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   */
  
  @Schema(name = "address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public UserInformation city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   */
  
  @Schema(name = "city", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public UserInformation phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
   */
  
  @Schema(name = "phone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phone")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public UserInformation phoneVerified(Boolean phoneVerified) {
    this.phoneVerified = phoneVerified;
    return this;
  }

  /**
   * Get phoneVerified
   * @return phoneVerified
   */
  
  @Schema(name = "phoneVerified", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phoneVerified")
  public Boolean getPhoneVerified() {
    return phoneVerified;
  }

  public void setPhoneVerified(Boolean phoneVerified) {
    this.phoneVerified = phoneVerified;
  }

  public UserInformation emailVerified(Boolean emailVerified) {
    this.emailVerified = emailVerified;
    return this;
  }

  /**
   * Get emailVerified
   * @return emailVerified
   */
  
  @Schema(name = "emailVerified", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("emailVerified")
  public Boolean getEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(Boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInformation userInformation = (UserInformation) o;
    return Objects.equals(this.id, userInformation.id) &&
        Objects.equals(this.email, userInformation.email) &&
        Objects.equals(this.fullName, userInformation.fullName) &&
        Objects.equals(this.authorities, userInformation.authorities) &&
        Objects.equals(this.nationID, userInformation.nationID) &&
        Objects.equals(this.address, userInformation.address) &&
        Objects.equals(this.city, userInformation.city) &&
        Objects.equals(this.phone, userInformation.phone) &&
        Objects.equals(this.phoneVerified, userInformation.phoneVerified) &&
        Objects.equals(this.emailVerified, userInformation.emailVerified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, fullName, authorities, nationID, address, city, phone, phoneVerified, emailVerified);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInformation {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    authorities: ").append(toIndentedString(authorities)).append("\n");
    sb.append("    nationID: ").append(toIndentedString(nationID)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    phoneVerified: ").append(toIndentedString(phoneVerified)).append("\n");
    sb.append("    emailVerified: ").append(toIndentedString(emailVerified)).append("\n");
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

