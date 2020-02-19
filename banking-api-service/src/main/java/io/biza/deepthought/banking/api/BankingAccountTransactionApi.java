package io.biza.deepthought.banking.api;

import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingTransactionListV1;
import io.biza.deepthought.banking.api.delegate.BankingAccountTransactionApiDelegate;
import io.biza.deepthought.banking.requests.RequestTransactionsByBulk;
import io.biza.deepthought.shared.support.CDRConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tags({
    @Tag(name = CDRConstants.TAG_BANKING_NAME, description = CDRConstants.TAG_BANKING_DESCRIPTION),
    @Tag(name = CDRConstants.TAG_ACCOUNTS_NAME,
        description = CDRConstants.TAG_ACCOUNTS_DESCRIPTION)})
@RequestMapping("/v1/banking/accounts/{accountId}/transactions")
public interface BankingAccountTransactionApi {

  default BankingAccountTransactionApiDelegate getDelegate() {
    return new BankingAccountTransactionApiDelegate() {};
  }

  @Operation(summary = "Get Transactions For Account",
      description = "Obtain transactions for a specific account.\n\nSome general notes that apply to all end points that retrieve transactions:\n\n- Where multiple transactions are returned, transactions should be ordered according to effective date in descending order\n- As the date and time for a transaction can alter depending on status and transaction type two separate date/times are included in the payload. There are still some scenarios where neither of these time stamps is available. For the purpose of filtering and ordering it is expected that the data holder will use the “effective” date/time which will be defined as:\n\t\t- Posted date/time if available, then\n\t\t- Execution date/time if available, then\n\t\t- A reasonable date/time nominated by the data holder using internal data structures\n- For transaction amounts it should be assumed that a negative value indicates a reduction of the available balance on the account while a positive value indicates an increase in the available balance on the account\n- For aggregated transactions (ie. groups of sub transactions reported as a single entry for the account) only the aggregated information, with as much consistent information accross the subsidiary transactions as possible, is required to be shared")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ, content = @Content(
          schema = @Schema(implementation = ResponseBankingTransactionListV1.class)))})
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_TRANSACTIONS_READ)
  default ResponseEntity<ResponseBankingTransactionListV1> getTransactions(
      @NotNull @Valid @PathVariable("accountId") UUID accountId,
      @Valid @RequestParam(name = "oldest-time", required = false) OffsetDateTime oldestTime,
      @Valid @RequestParam(name = "newest-time", required = false) OffsetDateTime newestTime,
      @Valid @RequestParam(name = "min-amount", required = false) BigDecimal minAmount,
      @Valid @RequestParam(name = "max-amount", required = false) BigDecimal maxAmount,
      @Valid @RequestParam(name = "text", required = false) String stringFilter,
      @Valid @RequestParam(name = "page", required = false,
          defaultValue = "1") @Min(1) Integer page,
      @Valid @RequestParam(name = "page-size", required = false,
          defaultValue = "25") Integer pageSize) {
    return getDelegate().getTransactions(accountId,
        RequestTransactionsByBulk.builder().oldestDateTime(oldestTime)
            .newestDateTime(newestTime).minAmount(minAmount).maxAmount(maxAmount)
            .stringFilter(stringFilter).page(page).pageSize(pageSize).build());
  }

  @Operation(summary = "Get Transaction Detail",
      description = "Obtain detailed information on a transaction for a specific account")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ,
      content = @Content(schema = @Schema(implementation = ResponseBankingAccountByIdV1.class)))})
  @GetMapping(value = "/{transactionId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_TRANSACTIONS_READ)
  default ResponseEntity<ResponseBankingAccountByIdV1> getTransactionDetail(
      @NotNull @Valid @PathVariable("accountId") UUID accountId, @NotNull @Valid @PathVariable("transactionId") UUID transactionId) {
    return getDelegate().getTransactionDetail(accountId, transactionId);
  }
}

