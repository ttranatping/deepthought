package io.biza.deepthought.admin.api;

import io.biza.deepthought.admin.Labels;
import io.biza.deepthought.admin.api.delegate.CustomerBankAccountAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.banking.DioBankBranch;
import io.biza.deepthought.data.payloads.dio.common.DioCustomer;
import io.biza.deepthought.data.payloads.dio.common.DioCustomerBankAccount;
import io.biza.deepthought.data.payloads.requests.RequestCustomerBankAccountConnection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Tag(name = Labels.TAG_CUSTOMER_NAME, description = Labels.TAG_CUSTOMER_DESCRIPTION)
@RequestMapping("/v1/brand/{brandId}/customer/{customerId}/bank-account")
public interface CustomerBankAccountAdminApi {

  default CustomerBankAccountAdminApiDelegate getDelegate() {
    return new CustomerBankAccountAdminApiDelegate() {};
  }

  @Operation(summary = "List all Customer Bank Accounts", description = "List all Customer Bank Accounts",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_CUSTOMER_READ})})
  @ApiResponses(value = {@ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
      description = Labels.RESPONSE_SUCCESSFUL_LIST, content = @Content(
          array = @ArraySchema(schema = @Schema(implementation = DioCustomerBankAccount.class))))})
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_CUSTOMER_READ)
  default ResponseEntity<List<DioCustomerBankAccount>> listCustomerBankAccountAssociations(
      @NotNull @Valid @PathVariable("brandId") UUID brandId, @NotNull @Valid @PathVariable("customerId") UUID customerId) {
    return getDelegate().listAssociations(brandId, customerId);
  }
  
  @Operation(summary = "Get a single Customer Account Association", description = "Returns a single customer bank account entry",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_CUSTOMER_READ})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_READ,
          content = @Content(schema = @Schema(implementation = DioCustomer.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NOT_FOUND,
          description = Labels.RESPONSE_OBJECT_NOT_FOUND)})
  @GetMapping(value = "/{associationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_CUSTOMER_READ)
  default ResponseEntity<DioCustomerBankAccount> getCustomerBankAccountAssociation(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("customerId") UUID customerId,
      @NotNull @Valid @PathVariable("associationId") UUID associationId) throws ValidationListException {
    return getDelegate().getAssociation(brandId, customerId, associationId);
  }

  @Operation(summary = "Unassociate a Customer Bank Account", description = "Removes a Customer Bank Account Association",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_CUSTOMER_WRITE})})

  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_DELETE,
          content = @Content(schema = @Schema(implementation = DioCustomer.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NOT_FOUND,
          description = Labels.RESPONSE_OBJECT_NOT_FOUND)})
  @DeleteMapping(path = "/{associationId}")
  @PreAuthorize(Labels.OAUTH2_SCOPE_CUSTOMER_WRITE)
  default ResponseEntity<Void> unassociateCustomerBankAccount(@NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("customerId") UUID customerId, @NotNull @Valid @PathVariable("associationId") UUID associationId) throws ValidationListException {
    return getDelegate().unassociateAccount(brandId, customerId, associationId);
  }
  
  @Operation(summary = "Associate a Customer Bank Account", description = "Associates a Bank Account with an individual Customer",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_BRANCH_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_CREATED,
          description = Labels.RESPONSE_SUCCESSFUL_CREATE,
          content = @Content(schema = @Schema(implementation = DioBankBranch.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_UNPROCESSABLE_ENTITY,
          description = Labels.RESPONSE_INPUT_VALIDATION_ERROR, content = @Content(
              array = @ArraySchema(schema = @Schema(implementation = ValidationListException.class))))})
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_CUSTOMER_WRITE)
  default ResponseEntity<DioCustomerBankAccount> associateCustomerBankAccount(@NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("customerId") UUID customerId,
      @NotNull @RequestBody RequestCustomerBankAccountConnection accountRequest) throws ValidationListException {
    return getDelegate().associateAccount(brandId, customerId, accountRequest);
  }

}
