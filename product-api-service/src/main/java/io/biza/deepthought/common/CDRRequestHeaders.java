package io.biza.deepthought.common;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import lombok.Data;

@Data
public class CDRRequestHeaders {

  /**
   * Is Authenticated Call?
   */
  Boolean isAuthenticated = false;
  
  /**
   * HTTP Method in use
   */
  HttpMethod httpMethod;

  /**
   * Content Types
   */
  String contentType;
  String acceptType = MediaType.APPLICATION_JSON_VALUE;

  /**
   * Version Headers
   */
  @NotNull
  Integer version;
  Integer minVersion;

  /**
   * FAPI Headers
   */
  UUID interactionId;
  OffsetDateTime customerAuthDate;
  String customerIpAddress;
  Map<String, String> customerClientHeaders;
  
  @AssertTrue(message = "Content-Type MUST be populated for PUT and POST calls")
  private boolean isContentTypePresent() {
    return contentType == null ? (!(HttpMethod.PUT.equals(httpMethod) || HttpMethod.POST.equals(httpMethod))) : true; 
  }
  
  @AssertTrue(message = "Content-Type MUST be set to application/json")
  private boolean isContentTypeValid() {
    return contentType != null ? MediaType.APPLICATION_JSON_VALUE.equals(contentType) : true;
  }

  @AssertTrue(message = "x-fapi-auth-date is MANDATORY for ALL authenticated calls")
  private boolean isAuthDatePresent() {
    return isAuthenticated ? customerAuthDate != null : true;
  }

  @AssertTrue(
      message = "x-cds-client-headers is MANDATORY and MUST contain a User-Agent Header when Customer Present activity is detected via x-fapi-customer-ip-address")
  private boolean isCustomerPresent() {
    return customerIpAddress != null
        ? (customerClientHeaders != null && customerClientHeaders.containsKey("User-Agent"))
        : true;
  }
}
