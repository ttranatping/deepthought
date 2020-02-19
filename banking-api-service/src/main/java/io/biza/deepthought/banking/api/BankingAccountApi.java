package io.biza.deepthought.banking.api;

import io.biza.babelfish.cdr.enumerations.BankingAccountStatusWithAll;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingAccountListV1;
import io.biza.deepthought.banking.api.delegate.BankingAccountApiDelegate;
import io.biza.deepthought.banking.requests.RequestListAccounts;
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
import org.springframework.web.bind.annotation.RequestParam;

@Tags({
    @Tag(name = CDRConstants.TAG_BANKING_NAME, description = CDRConstants.TAG_BANKING_DESCRIPTION),
    @Tag(name = CDRConstants.TAG_ACCOUNTS_NAME,
        description = CDRConstants.TAG_ACCOUNTS_DESCRIPTION)})
public interface BankingAccountApi {

  default BankingAccountApiDelegate getDelegate() {
    return new BankingAccountApiDelegate() {};
  }

  @Operation(summary = "Get Accounts", description = "Obtain a list of accounts")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ,
      content = @Content(schema = @Schema(implementation = ResponseBankingAccountListV1.class)))})
  @GetMapping(value = "/v1/banking/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_ACCOUNT_BASIC_READ)
  default ResponseEntity<ResponseBankingAccountListV1> listAccounts(
      @Valid @RequestParam(name = "product-category",
          required = false) BankingProductCategory productCategory,
      @Valid @RequestParam(name = "open-status", required = false,
          defaultValue = "ALL") BankingAccountStatusWithAll accountStatus,
      @Valid @RequestParam(name = "page", required = false,
          defaultValue = "1") @Min(1) Integer page,
      @Valid @RequestParam(name = "page-size", required = false,
          defaultValue = "25") Integer pageSize) {
    return getDelegate().listAccounts(RequestListAccounts.builder().productCategory(productCategory)
        .accountStatus(accountStatus).page(page).pageSize(pageSize).build());
  }

  @Operation(summary = "Get Account Detail",
      description = "Obtain detailed information on a single account")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ,
      content = @Content(schema = @Schema(implementation = ResponseBankingAccountByIdV1.class)))})
  @GetMapping(value = "/v1/banking/accounts/{accountId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_ACCOUNT_DETAIL_READ)
  default ResponseEntity<ResponseBankingAccountByIdV1> getAccountDetail(
      @NotNull @Valid @PathVariable("accountId") UUID accountId) {
    return getDelegate().getAccountDetail(accountId);
  }
}

