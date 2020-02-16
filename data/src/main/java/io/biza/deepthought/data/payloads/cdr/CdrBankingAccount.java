package io.biza.deepthought.data.payloads.cdr;

import java.util.List;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountCreditCard;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountLoanAccount;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountTermDeposit;
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
@Schema(description = "CDR Bank Account Specifics")
public class CdrBankingAccount {

  @Schema(description = "Term Deposit Details")
  @JsonProperty("termDeposits")
  List<DioBankAccountTermDeposit> termDeposits;
  
  @Schema(description = "Credit Card Details")
  @JsonProperty("creditCards")
  List<DioBankAccountCreditCard> creditCards;
  
  @Schema(description = "Loan Account Details")
  @JsonProperty("loanAccounts")
  List<DioBankAccountLoanAccount> loanAccounts;
}
