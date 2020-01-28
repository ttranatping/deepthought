package io.biza.deepthought.data.payload.cdr;

import java.net.URI;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.biza.babelfish.cdr.converters.DateTimeStringToOffsetDateTimeConverter;
import io.biza.babelfish.cdr.converters.OffsetDateTimeToDateTimeStringConverter;
import io.biza.babelfish.cdr.converters.UriToUriStringConverter;
import io.biza.babelfish.cdr.v1.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.v1.model.banking.BankingProductAdditionalInformation;
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
@Schema(description = "CDR Formatted Banking Product")
public class CdrBankingProduct {

  @Schema(
      description = "The date and time from which this product is effective (ie. is available for origination).  Used to enable the articulation of products to the regime before they are available for customers to originate",
      type = "string")
  @JsonSerialize(converter = OffsetDateTimeToDateTimeStringConverter.class)
  @JsonDeserialize(converter = DateTimeStringToOffsetDateTimeConverter.class)
  @JsonProperty("effectiveFrom")
  OffsetDateTime effectiveFrom;

  @Schema(
      description = "The date and time at which this product will be retired and will no longer be offered.  Used to enable the managed deprecation of products",
      type = "string")
  @JsonSerialize(converter = OffsetDateTimeToDateTimeStringConverter.class)
  @JsonDeserialize(converter = DateTimeStringToOffsetDateTimeConverter.class)
  @JsonProperty("effectiveTo")
  OffsetDateTime effectiveTo;

  @Schema(
      description = "The last date and time that the information for this product was changed (or the creation date for the product if it has never been altered)",
      type = "string", accessMode = AccessMode.READ_ONLY)
  @JsonSerialize(converter = OffsetDateTimeToDateTimeStringConverter.class)
  @JsonDeserialize(converter = DateTimeStringToOffsetDateTimeConverter.class)
  @JsonProperty("lastUpdated")
  OffsetDateTime lastUpdated;

  @Schema(required = true, description = "Product Category this product belongs to")
  @JsonProperty("productCategory")
  @NonNull
  @NotNull
  BankingProductCategory productCategory;

  @Schema(description = "A link to an application web page where this product can be applied for.",
      type = "string")
  @JsonSerialize(converter = UriToUriStringConverter.class)
  @JsonProperty("applicationUri")
  @Valid
  URI applicationUri;

  @Schema(
      description = "Indicates whether the product is specifically tailored to a circumstance.  In this case fees and prices are significantly negotiated depending on context. While all products are open to a degree of tailoring this flag indicates that tailoring is expected and thus that the provision of specific fees and rates is not applicable",
      required = true)
  @NonNull
  @NotNull
  @JsonProperty("isTailored")
  Boolean tailored;

  @Schema(description = "Additional Information for Banking Product")
  @JsonProperty("additionalInformation")
  BankingProductAdditionalInformation additionalInformation;

}
