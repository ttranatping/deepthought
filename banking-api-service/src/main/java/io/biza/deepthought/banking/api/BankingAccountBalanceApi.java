package io.biza.deepthought.banking.api;

import io.biza.babelfish.cdr.enumerations.BankingAccountStatusWithAll;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.models.requests.RequestAccountIdsV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountListV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountsBalanceByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountsBalanceListV1;
import io.biza.deepthought.banking.api.delegate.BankingAccountBalanceApiDelegate;
import io.biza.deepthought.banking.requests.RequestBalancesByAccounts;
import io.biza.deepthought.banking.requests.RequestBalancesByBulk;
import io.biza.deepthought.shared.support.CDRConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tags({
    @Tag(name = CDRConstants.TAG_BANKING_NAME, description = CDRConstants.TAG_BANKING_DESCRIPTION),
    @Tag(name = CDRConstants.TAG_ACCOUNTS_NAME,
        description = CDRConstants.TAG_ACCOUNTS_DESCRIPTION)})
public interface BankingAccountBalanceApi {

  default BankingAccountBalanceApiDelegate getDelegate() {
    return new BankingAccountBalanceApiDelegate() {};
  }

  @Operation(summary = "Get Bulk Balances",
      description = "Obtain balances for multiple, filtered accounts")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ, content = @Content(
          schema = @Schema(implementation = ResponseBankingAccountsBalanceListV1.class)))})
  @GetMapping(value = "/v1/banking/accounts/balances", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_ACCOUNT_BASIC_READ)
  default ResponseEntity<ResponseBankingAccountListV1> listBalancesBulk(
      @Valid @RequestParam(name = "product-category",
          required = false) BankingProductCategory productCategory,
      @Valid @RequestParam(name = "open-status", required = false,
          defaultValue = "ALL") BankingAccountStatusWithAll accountStatus,
      @Valid @RequestParam(name = "is-owned", required = false) Boolean isOwned,
      @Valid @RequestParam(name = "page", required = false,
          defaultValue = "1") @Min(1) Integer page,
      @Valid @RequestParam(name = "page-size", required = false,
          defaultValue = "25") Integer pageSize) {
    return getDelegate()
        .listBalancesBulk(RequestBalancesByBulk.builder().productCategory(productCategory)
            .accountStatus(accountStatus).isOwned(isOwned).page(page).pageSize(pageSize).build());
  }

  @Operation(summary = "Get Balances For Specific Accounts",
      description = "Obtain balances for a specified list of accounts")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ, content = @Content(
          schema = @Schema(implementation = ResponseBankingAccountsBalanceListV1.class)))})
  @PostMapping(value = "/v1/banking/accounts/balances", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_ACCOUNT_BASIC_READ)
  default ResponseEntity<ResponseBankingAccountsBalanceListV1> listBalancesSpecificAccounts(
      @Valid @RequestParam(name = "page", required = false,
          defaultValue = "1") @Min(1) Integer page,
      @Valid @RequestParam(name = "page-size", required = false,
          defaultValue = "25") Integer pageSize,
      @Valid @RequestBody RequestAccountIdsV1 requestAccountIds) {
    return getDelegate().listAccountBalances(RequestBalancesByAccounts.builder()
        .accountIds(requestAccountIds).page(page).pageSize(pageSize).build());
  }

  @Operation(summary = "Get Account Balance",
      description = "Obtain the balance for a single specified account")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ, content = @Content(
          schema = @Schema(implementation = ResponseBankingAccountsBalanceListV1.class)))})
  @GetMapping(value = "/v1/banking/accounts/{accountId}/balance", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_ACCOUNT_BASIC_READ)
  default ResponseEntity<ResponseBankingAccountsBalanceByIdV1> getBalance(
      @NotNull @Valid @PathVariable("accountId") UUID accountId) {
    return getDelegate().getAccountBalance(accountId);
  }

}

