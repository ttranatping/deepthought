package io.biza.deepthought.banking.api;

import io.biza.babelfish.cdr.enumerations.BankingAccountStatusWithAll;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.models.requests.RequestAccountIdsV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountsBalanceListV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingDirectDebitAuthorisationListV1;
import io.biza.deepthought.banking.api.delegate.BankingAccountDirectDebitApiDelegate;
import io.biza.deepthought.banking.requests.RequestDirectDebitsByAccounts;
import io.biza.deepthought.banking.requests.RequestDirectDebitsByBulk;
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
    @Tag(name = CDRConstants.TAG_DIRECT_DEBITS_NAME,
        description = CDRConstants.TAG_DIRECT_DEBITS_DESCRIPTION)})
public interface BankingAccountDirectDebitApi {

  default BankingAccountDirectDebitApiDelegate getDelegate() {
    return new BankingAccountDirectDebitApiDelegate() {};
  }

  @Operation(summary = "Get Direct Debits For Account",
      description = "Obtain direct debit authorisations for a specific account")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ, content = @Content(
          schema = @Schema(implementation = ResponseBankingDirectDebitAuthorisationListV1.class)))})
  @GetMapping(value = "/v1/banking/accounts/{accountId}/direct-debits", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_REGULAR_PAYMENTS_READ)
  default ResponseEntity<ResponseBankingDirectDebitAuthorisationListV1> listDirectDebits(
      @NotNull @Valid @PathVariable("accountId") UUID accountId,
      @Valid @RequestParam(name = "page", required = false,
          defaultValue = "1") @Min(1) Integer page,
      @Valid @RequestParam(name = "page-size", required = false,
          defaultValue = "25") Integer pageSize) {
    return getDelegate().listByAccount(accountId,
        RequestDirectDebitsByAccounts.builder().page(page).pageSize(pageSize).build());
  }
  
  @Operation(summary = "Get Bulk Direct Debits",
      description = "Obtain direct debit authorisations for multiple, filtered accounts")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ, content = @Content(
          schema = @Schema(implementation = ResponseBankingAccountsBalanceListV1.class)))})
  @GetMapping(value = "/v1/banking/accounts/direct-debits", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_REGULAR_PAYMENTS_READ)
  default ResponseEntity<ResponseBankingDirectDebitAuthorisationListV1> listDirectDebitsBulk(
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
        .listAll(RequestDirectDebitsByBulk.builder().productCategory(productCategory)
            .accountStatus(accountStatus).isOwned(isOwned).page(page).pageSize(pageSize).build());
  }

  @Operation(summary = "Get Direct Debits For Specific Accounts",
      description = "Obtain direct debit authorisations for a specified list of accounts")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ, content = @Content(
          schema = @Schema(implementation = ResponseBankingAccountsBalanceListV1.class)))})
  @PostMapping(value = "/v1/banking/accounts/direct-debits", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_REGULAR_PAYMENTS_READ)
  default ResponseEntity<ResponseBankingDirectDebitAuthorisationListV1> listDirectDebitsSpecificAccounts(
      @Valid @RequestParam(name = "page", required = false,
          defaultValue = "1") @Min(1) Integer page,
      @Valid @RequestParam(name = "page-size", required = false,
          defaultValue = "25") Integer pageSize,
      @Valid @RequestBody RequestAccountIdsV1 requestAccountIds) {
    return getDelegate().listByAccountList(RequestDirectDebitsByAccounts.builder()
        .accountIds(requestAccountIds).page(page).pageSize(pageSize).build());
  }

}

