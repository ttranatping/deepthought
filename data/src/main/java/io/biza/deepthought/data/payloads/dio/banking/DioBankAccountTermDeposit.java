package io.biza.deepthought.data.payloads.dio.banking;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.biza.babelfish.cdr.converters.DateTimeStringToOffsetDateTimeConverter;
import io.biza.babelfish.cdr.converters.OffsetDateTimeToDateTimeStringConverter;
import io.biza.deepthought.data.enumerations.DioAccountStatus;
import io.biza.deepthought.data.enumerations.DioBankAccountType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payloads.cdr.CdrBankingAccount;
import io.biza.deepthought.data.payloads.dio.product.DioProduct;
import io.biza.deepthought.data.payloads.dio.product.DioProductBundle;
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
@Schema(description = "A Deep Thought Bank Account Container")
public class DioBankAccountTermDeposit {

  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Bank Account Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);

  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Scheme Type", defaultValue = "CDR_BANKING")
  public DioSchemeType schemeType;
  
  @Schema(
      description = "Creation Date Time",
      type = "string", format = "date-time", accessMode = AccessMode.READ_ONLY)
  @JsonSerialize(converter = OffsetDateTimeToDateTimeStringConverter.class)
  @JsonDeserialize(converter = DateTimeStringToOffsetDateTimeConverter.class)
  @JsonProperty("lastUpdated")
  OffsetDateTime creationDateTime;
  
  @Schema(
      description = "Open or closed status for the account. If not present then OPEN is assumed")
  @JsonProperty(value = "openStatus", defaultValue = "OPEN")
  @Builder.Default
  DioAccountStatus status = DioAccountStatus.OPEN;
  
  @Schema(description = "The category to which a product or account belongs.", required = true)
  @NotNull
  @JsonProperty("productCategory")
  DioBankAccountType accountType;
    
  @Schema(
      description = "The display name of the account as defined by the bank. This should not incorporate account numbers or PANs. If it does the values should be masked according to the rules of the MaskedAccountString common type.",
      required = true)
  @NotEmpty(message = "Must contain a display name for the account")
  @JsonProperty("displayName")
  String displayName;
  
  @Schema(description = "A customer supplied nick name for the account")
  @JsonProperty("nickName")
  String nickName;
  
  @Schema(description = "Account Number")
  @JsonProperty("accountNumber")
  Integer accountNumber;
  
  @Schema(description = "Associated Product Bundle")
  @JsonProperty("bundle")
  DioProductBundle bundle;
  
  @Schema(description = "Associated Product")
  @JsonProperty("product")
  DioProduct product;
  
  @JsonProperty("cdrBanking")
  @Schema(description = "CDR Banking Account")
  @Valid
  @NotNull
  public CdrBankingAccount cdrBanking;

}
