package io.biza.deepthought.data.payloads;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.biza.babelfish.cdr.converters.DateTimeStringToOffsetDateTimeConverter;
import io.biza.babelfish.cdr.converters.OffsetDateTimeToDateTimeStringConverter;
import io.biza.deepthought.data.enumerations.DioPersonPrefix;
import io.biza.deepthought.data.enumerations.DioPersonSuffix;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payloads.cdr.CdrCommonPerson;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
@Schema(description = "A Deep Thought Customer Container")
public class DioPerson {

  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Person Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);

  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Scheme Type", defaultValue = "CDR_COMMON")
  @Builder.Default
  public DioSchemeType schemeType = DioSchemeType.CDR_COMMON;
  
  @Schema(
      description = "Creation Date Time",
      type = "string", format = "date-time", accessMode = AccessMode.READ_ONLY)
  @JsonSerialize(converter = OffsetDateTimeToDateTimeStringConverter.class)
  @JsonDeserialize(converter = DateTimeStringToOffsetDateTimeConverter.class)
  @JsonProperty("created")
  OffsetDateTime created;
  
  @Schema(
      description = "Last Update Date Time",
      type = "string", format = "date-time", accessMode = AccessMode.READ_ONLY)
  @JsonSerialize(converter = OffsetDateTimeToDateTimeStringConverter.class)
  @JsonDeserialize(converter = DateTimeStringToOffsetDateTimeConverter.class)
  @JsonProperty("lastUpdated")
  OffsetDateTime lastUpdated;
  
  @Schema(
      description = "Also known as title or salutation.  The prefix to the name (e.g. Mr, Mrs, Ms, Miss, Sir, etc)")
  @JsonProperty("prefix")
  DioPersonPrefix prefix;
  
  @Schema(
      description = "For people with single names this field need not be present.  The single name should be in the lastName field")
  @JsonProperty("firstName")
  String firstName;

  @Schema(description = "For people with single names the single name should be in this field",
      required = true)
  @JsonProperty("lastName")
  @NotEmpty(
      message = "Should be populated with last name or, for single names, this field should be used")
  String lastName;

  @Schema(description = "Field is mandatory but array may be empty", required = true)
  @JsonProperty("middleNames")
  @NotNull
  @Builder.Default
  List<String> middleNames = List.of();

  @Schema(description = "Used for a trailing suffix to the name (e.g. Jr)")
  @JsonProperty("suffix")
  DioPersonSuffix suffix;
  
  @Schema(description = "Preferred Phone Number")
  @JsonProperty("phone")
  DioPhoneNumber phone;
  
  @Schema(description = "Preferred Email")
  @JsonProperty("email")
  DioEmail email;
  
  @Schema(description = "Preferred Address")
  @JsonProperty("address")
  DioAddress address;
  
  @JsonProperty("cdrCommon")
  @Schema(description = "CDR Banking Person")
  @Valid
  @NotNull
  @NonNull
  public CdrCommonPerson cdrCommon;

}
