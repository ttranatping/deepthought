package io.biza.deepthought.data.payloads.dio.banking;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Currency;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.biza.deepthought.data.enumerations.DioMaturityInstructionType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.babelfish.cdr.converters.CurrencyToStringConverter;
import io.biza.babelfish.cdr.converters.LocalDateToStringConverter;
import io.biza.babelfish.cdr.converters.PeriodToStringConverter;
import io.biza.babelfish.cdr.converters.StringToCurrencyConverter;
import io.biza.babelfish.cdr.converters.StringToLocalDateConverter;
import io.biza.babelfish.cdr.converters.StringToPeriodConverter;
import io.biza.deepthought.data.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
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
      description = "Term Deposit Currency", type = "string")
  @Builder.Default
  @JsonSerialize(converter = CurrencyToStringConverter.class)
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency currency = Currency.getInstance(Constants.DEFAULT_CURRENCY);
  
  @Schema(
      description = "Term Deposit Interet Rate")
  @NotNull
  BigDecimal rate;
  
  @Schema(
      description = "Term Deposit Start Date/Time", type = "string", format = "date-time")
  @NotNull
  OffsetDateTime lodgement;
  
  @Schema(
      description = "Term Deposit Term Length", type = "string")
  @NotNull
  @JsonSerialize(converter = PeriodToStringConverter.class)
  @JsonDeserialize(converter = StringToPeriodConverter.class)
  Period termLength;
  
  @Schema(
      description = "Calculation Frequency", type = "string")
  @NotNull
  @JsonSerialize(converter = PeriodToStringConverter.class)
  @JsonDeserialize(converter = StringToPeriodConverter.class)
  Period calculationFrequency;
  
  @Schema(
      description = "Last Calculated Date/Time", format = "date-time", type = "string")
  @NotNull
  OffsetDateTime lastCalculated;
  
  @Schema(
      description = "Application Frequency", type = "string")
  @NotNull
  @JsonSerialize(converter = PeriodToStringConverter.class)
  @JsonDeserialize(converter = StringToPeriodConverter.class)
  Period applicationFrequency;
  
  @Schema(
      description = "Last Time Interest was Applied", type = "string", format = "date-time")
  @NotNull
  OffsetDateTime lastApplied;
  
  @Schema(
      description = "Maturity Instructions")
  @NotNull
  DioMaturityInstructionType maturityInstruction;

}
