package io.biza.deepthought.banking.api;

import io.biza.babelfish.cdr.enumerations.BankingAccountStatusWithAll;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.models.requests.RequestAccountIdsV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingScheduledPaymentsListV1;
import io.biza.deepthought.banking.api.delegate.BankingAccountScheduledPaymentApiDelegate;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByAccounts;
import io.biza.deepthought.banking.requests.RequestScheduledPaymentsByBulk;
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
    @Tag(name = CDRConstants.TAG_SCHEDULED_PAYMENTS_NAME,
        description = CDRConstants.TAG_SCHEDULED_PAYMENTS_DESCRIPTION)})
public interface BankingAccountScheduledPaymentApi {

  default BankingAccountScheduledPaymentApiDelegate getDelegate() {
    return new BankingAccountScheduledPaymentApiDelegate() {};
  }

  @Operation(summary = "Get Scheduled Payments For Account",
      description = "Obtain scheduled, outgoing payments for a specific account")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ, content = @Content(
          schema = @Schema(implementation = ResponseBankingScheduledPaymentsListV1.class)))})
  @GetMapping(value = "/v1/banking/accounts/{accountId}/payments/scheduled", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_REGULAR_PAYMENTS_READ)
  default ResponseEntity<ResponseBankingScheduledPaymentsListV1> listScheduledPayments(
      @NotNull @Valid @PathVariable("accountId") UUID accountId,
      @Valid @RequestParam(name = "page", required = false,
          defaultValue = "1") @Min(1) Integer page,
      @Valid @RequestParam(name = "page-size", required = false,
          defaultValue = "25") Integer pageSize) {
    return getDelegate().listByAccount(accountId,
        RequestScheduledPaymentsByAccounts.builder().page(page).pageSize(pageSize).build());
  }
  
  @Operation(summary = "Get Scheduled Payments Bulk",
      description = "Obtain scheduled payments for multiple, filtered accounts that are the source of funds for the payments")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ, content = @Content(
          schema = @Schema(implementation = ResponseBankingScheduledPaymentsListV1.class)))})
  @GetMapping(value = "/v1/banking/accounts/payments/scheduled", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_REGULAR_PAYMENTS_READ)
  default ResponseEntity<ResponseBankingScheduledPaymentsListV1> listScheduledPaymentsBulk(
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
        .listAll(RequestScheduledPaymentsByBulk.builder().productCategory(productCategory)
            .accountStatus(accountStatus).isOwned(isOwned).page(page).pageSize(pageSize).build());
  }

  @Operation(summary = "Get Scheduled Payments For Specific Accounts",
      description = "Obtain scheduled payments for a specified list of accounts")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ, content = @Content(
          schema = @Schema(implementation = ResponseBankingScheduledPaymentsListV1.class)))})
  @PostMapping(value = "/v1/banking/accounts/payments/scheduled", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_REGULAR_PAYMENTS_READ)
  default ResponseEntity<ResponseBankingScheduledPaymentsListV1> listScheduledPaymentsSpecificAccounts(
      @Valid @RequestParam(name = "page", required = false,
          defaultValue = "1") @Min(1) Integer page,
      @Valid @RequestParam(name = "page-size", required = false,
          defaultValue = "25") Integer pageSize,
      @Valid @RequestBody RequestAccountIdsV1 requestAccountIds) {
    return getDelegate().listByAccountList(RequestScheduledPaymentsByAccounts.builder()
        .accountIds(requestAccountIds).page(page).pageSize(pageSize).build());
  }

}

