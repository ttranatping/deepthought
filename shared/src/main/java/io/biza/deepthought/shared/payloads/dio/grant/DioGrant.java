package io.biza.deepthought.shared.payloads.dio.grant;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Valid
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A single Grant")
public class DioGrant {

  @Schema(description = "Grant Identifier", required = true)
  @JsonProperty("id")
  public UUID id;

  @Schema(description = "Token Subject", required = true)
  @JsonProperty("subject")
  @NotNull
  public String subject;

  @Schema(description = "Customers in Grant", required = true)
  @JsonProperty("customers")
  @ToString.Exclude
  @NotNull
  // TODO: Support more than one customer in a single grant
  @Size(message = "Only one customer per grant is currently supported", max = 1) 
  Set<DioGrantCustomer> customers;

  @Schema(description = "Accounts included in Grant", required = true)
  @JsonProperty("accounts")
  @NotNull
  Set<DioGrantAccount> accounts;
  
  @Schema(description = "Creation time of Grant", accessMode = AccessMode.READ_ONLY, type = "string", format = "date-time")
  OffsetDateTime created;
  
  @Schema(description = "Grant Expiry time", type = "string", format = "date-time")
  @JsonProperty("expiry")
  @NotNull
  OffsetDateTime expiry;

}
