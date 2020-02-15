package io.biza.deepthought.data.payloads.cdr;

import java.util.List;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingCreditCardAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingLoanAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingTermDepositAccountV1;
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
  @JsonProperty("termDeposit")
  List<BankingTermDepositAccountV1> termDeposit;
  
  @Schema(description = "Credit Card Details")
  @JsonProperty("creditCard")
  List<BankingCreditCardAccountV1> creditCard;
  
  @Schema(description = "Loan Account Details")
  @JsonProperty("loanAccount")
  List<BankingLoanAccountV1> loanAccount;
}
