package io.biza.deepthought.shared.payloads.dio.banking;

import java.math.BigDecimal;
import java.util.Currency;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.biza.babelfish.cdr.converters.CurrencyToStringConverter;
import io.biza.babelfish.cdr.converters.StringToCurrencyConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Valid
@ToString
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A Representation of a Banking Account Balance", name = "BankingBalanceV1")
public class DioBankAccountBalance {
  
  @Schema(description = "A unique ID of the account adhering to the standards for ID permanence",
      required = true)
  @NotEmpty
  @JsonProperty("accountId")
  String accountId;

  @Schema(
      description = "The balance of the account at this time.",
      required = true)
  @NotNull
  @JsonProperty("currentBalance")
  BigDecimal currentBalance;

  @Schema(
      description = "Balance representing the amount of funds available for transfer.",
      required = true)
  @NotNull
  @JsonProperty("availableBalance")
  BigDecimal availableBalance;

  @Schema(
      description = "Object representing the maximum amount of credit that is available for this account.")
  @JsonProperty(value = "creditLimit", defaultValue = "0")
  @Builder.Default
  BigDecimal creditLimit = BigDecimal.ZERO;

  @Schema(
      description = "Object representing the available limit amortised according to payment schedule.")
  @JsonProperty(value = "amortisedLimit", defaultValue = "0")
  @Builder.Default
  BigDecimal amortisedLimit = BigDecimal.ZERO;

  @Schema(description = "The currency for the balance amounts. If absent assumed to be AUD",
      type = "string")
  @JsonSerialize(converter = CurrencyToStringConverter.class)
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  @JsonProperty(value = "currency", defaultValue = "AUD")
  @Builder.Default
  Currency currency = Currency.getInstance("AUD");

}
