package io.biza.deepthought.data.payloads.requests;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.data.enumerations.DioAccountStatus;
import io.biza.deepthought.data.enumerations.DioBankAccountType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountCreditCard;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountLoanAccount;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountTermDeposit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Valid
@ToString
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for create or update of a Bank Account")
public class RequestBankAccount {

  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Scheme Type", defaultValue = "DIO_BANKING")
  @Builder.Default
  public DioSchemeType schemeType = DioSchemeType.DIO_BANKING;

  @Schema(
      description = "Open or closed status for the account. If not present then OPEN is assumed")
  @JsonProperty(value = "status", defaultValue = "OPEN")
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
  
  @Schema(description = "Account ID to assign the new account")
  @JsonProperty("accountNumber")
  Integer accountNumber;

  @Schema(description = "Associated Product Bundle")
  @JsonProperty("bundleId")
  UUID bundleId;

  @Schema(description = "Associated Product ID", required = true)
  @JsonProperty("product")
  @NotNull
  UUID productId;

  @Schema(description = "Term Deposit Details")
  @JsonProperty("termDeposit")
  DioBankAccountTermDeposit termDeposit;

  @Schema(description = "Credit Card Details")
  @JsonProperty("creditCard")
  DioBankAccountCreditCard creditCard;

  @Schema(description = "Loan Account Details")
  @JsonProperty("loanAccount")
  DioBankAccountLoanAccount loanAccount;

  @AssertTrue(
      message = "Zero or Only One of termDeposits, creditCards or loanAccounts can be provided")
  private Boolean isOnlyOneSubDetailProvided() {
    return (termDeposit != null && creditCard == null && loanAccount == null)
        || (termDeposit == null && creditCard != null && loanAccount == null)
        || (termDeposit == null && creditCard == null && loanAccount != null);
  }

  @AssertTrue(
      message = "Product Category must be aligned when termDeposits, creditCards or loanAccounts provided")
  private Boolean isProductCategoryAccurate() {
    return (termDeposit != null && accountType == DioBankAccountType.TERM_DEPOSITS)
        || (creditCard != null && accountType == DioBankAccountType.CRED_AND_CHRG_CARDS)
        || (loanAccount != null && List.of(DioBankAccountType.BUSINESS_LOANS,
            DioBankAccountType.MARGIN_LOANS, DioBankAccountType.PERS_LOANS).contains(accountType));
  }

}
