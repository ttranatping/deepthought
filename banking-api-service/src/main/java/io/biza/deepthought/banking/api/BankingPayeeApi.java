package io.biza.deepthought.banking.api;

import io.biza.babelfish.cdr.enumerations.BankingPayeeTypeWithAll;
import io.biza.babelfish.cdr.models.responses.ResponseBankingPayeeByIdV1;
import io.biza.babelfish.cdr.models.responses.ResponseBankingPayeeListV1;
import io.biza.deepthought.banking.api.delegate.BankingPayeeApiDelegate;
import io.biza.deepthought.banking.requests.RequestListPayees;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tags({
    @Tag(name = CDRConstants.TAG_BANKING_NAME, description = CDRConstants.TAG_BANKING_DESCRIPTION),
    @Tag(name = CDRConstants.TAG_PAYEES_NAME,
        description = CDRConstants.TAG_PAYEES_DESCRIPTION)})
@RequestMapping("/v1/banking/payees")
public interface BankingPayeeApi {

  default BankingPayeeApiDelegate getDelegate() {
    return new BankingPayeeApiDelegate() {};
  }

  @Operation(summary = "Get Payees", description = "Obtain a list of pre-registered payees")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ,
      content = @Content(schema = @Schema(implementation = ResponseBankingPayeeListV1.class)))})
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_BANK_PAYEES_READ)
  default ResponseEntity<ResponseBankingPayeeListV1> listPayees(
      @Valid @RequestParam(name = "type", required = false,
          defaultValue = "ALL") BankingPayeeTypeWithAll payeeType,
      @Valid @RequestParam(name = "page", required = false,
          defaultValue = "1") @Min(1) Integer page,
      @Valid @RequestParam(name = "page-size", required = false,
          defaultValue = "25") Integer pageSize) {
    return getDelegate().listPayees(RequestListPayees.builder()
        .payeeType(payeeType).page(page).pageSize(pageSize).build());
  }

  @Operation(summary = "Get Payee Detail",
      description = "Obtain detailed information on a single account")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ,
      content = @Content(schema = @Schema(implementation = ResponseBankingPayeeByIdV1.class)))})
  @GetMapping(value = "/{payeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_BANK_BANK_PAYEES_READ)
  default ResponseEntity<ResponseBankingPayeeByIdV1> getPayeeDetail(
      @NotNull @Valid @PathVariable("payeeId") UUID payeeId) {
    return getDelegate().getPayeeDetail(payeeId);
  }
}

