package io.biza.deepthought.data.payloads.dio.banking;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Currency;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import io.biza.deepthought.data.enumerations.DioMaturityInstructionType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payloads.cdr.CdrBankingAccount;
import io.biza.deepthought.data.Constants;
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
@Schema(description = "A Deep Thought Bank Term Deposit Account Container")
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
  @Schema(description = "Deep Thought Scheme Type", defaultValue = "DIO_BANKING")
  @Builder.Default
  public DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  
  @Schema(
      description = "Term Deposit Start Amount")
  @NotNull
  BigDecimal amount;
  
  @Schema(
      description = "Term Deposit Currency")
  @Builder.Default
  Currency currency = Currency.getInstance(Constants.DEFAULT_CURRENCY);
  
  @Schema(
      description = "Term Deposit Interet Rate")
  @NotNull
  BigDecimal rate;
  
  @Schema(
      description = "Term Deposit Start Date/Time")
  @NotNull
  OffsetDateTime lodgement;
  
  @Schema(
      description = "Term Deposit Term Length")
  @NotNull
  Period termLength;
  
  @Schema(
      description = "Calculation Frequency")
  @NotNull
  Period calculationFrequency;
  
  @Schema(
      description = "Last Calculated Date/Time")
  @NotNull
  OffsetDateTime lastCalculated;
  
  @Schema(
      description = "Application Frequency")
  @NotNull
  Period applicationFrequency;
  
  @Schema(
      description = "Last Time Interest was Applied")
  @NotNull
  OffsetDateTime lastApplied;
  
  @Schema(
      description = "Maturity Instructions")
  @NotNull
  DioMaturityInstructionType maturityInstruction;

}
