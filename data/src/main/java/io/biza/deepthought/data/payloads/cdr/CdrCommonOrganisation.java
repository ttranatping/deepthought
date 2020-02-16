package io.biza.deepthought.data.payloads.cdr;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.babelfish.cdr.enumerations.CommonOrganisationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Valid
@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CDR Organisation Attributes")
public class CdrCommonOrganisation {

  @Schema(description = "Australian Business Number for the organisation")
  @JsonProperty("abn")
  String abn;

  @Schema(
      description = "Australian Company Number for the organisation. Required only if an ACN is applicable for the organisation type")
  @JsonProperty("acn")
  String acn;

  @Schema(
      description = "True if registered with the ACNC.  False if not. Absent or null if not confirmed.")
  @JsonProperty("isACNCRegistered")
  Boolean isACNCRegistered;

  @Schema(description = "[ANZSIC (2006)](http://www.abs.gov.au/anzsic) code for the organisation.")
  @JsonProperty("industryCode")
  String industryCode;

  @Schema(description = "Legal organisation type", required = true)
  @JsonProperty("organisationType")
  @NotNull
  @Valid
  CommonOrganisationType organisationType;

  @Schema(
      description = "Enumeration with values from [ISO 3166 Alpha-3](https://www.iso.org/iso-3166-country-codes.html) country codes.", type = "string")
  @JsonProperty("registeredCountry")
  String registeredCountry;

  @Schema(description = "The date the organisation described was established", type = "string", format = "date")
  @JsonProperty("establishmentDate")
  String establishmentDate;


}
