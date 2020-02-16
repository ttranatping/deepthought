package io.biza.deepthought.admin.api;

import io.biza.deepthought.admin.Labels;
import io.biza.deepthought.admin.api.delegate.BranchAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.banking.DioBranch;
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

@Tag(name = Labels.TAG_BRANCH_NAME, description = Labels.TAG_BRANCH_DESCRIPTION)
@RequestMapping("/v1/brand/{brandId}/branch")
public interface BranchAdminApi {

  default BranchAdminApiDelegate getDelegate() {
    return new BranchAdminApiDelegate() {};
  }

  @Operation(summary = "List all Branches", description = "List all Branches",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_BRANCH_READ})})
  @ApiResponses(value = {@ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
      description = Labels.RESPONSE_SUCCESSFUL_LIST, content = @Content(
          array = @ArraySchema(schema = @Schema(implementation = DioBranch.class))))})
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_BRANCH_READ)
  default ResponseEntity<List<DioBranch>> listBranches(
      @NotNull @Valid @PathVariable("brandId") UUID brandId) {
    return getDelegate().listBranches(brandId);
  }
  
  @Operation(summary = "Get a single Branch", description = "Returns a single branch entry",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_BRANCH_READ})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_READ,
          content = @Content(schema = @Schema(implementation = DioBranch.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NOT_FOUND,
          description = Labels.RESPONSE_OBJECT_NOT_FOUND)})
  @GetMapping(value = "/{branchId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_BRANCH_READ)
  default ResponseEntity<DioBranch> getBranch(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("branchId") UUID branchId) {
    return getDelegate().getBranch(brandId, branchId);
  }

  @Operation(summary = "Create a Branch", description = "Creates and Returns a new Branch",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_BRANCH_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_CREATED,
          description = Labels.RESPONSE_SUCCESSFUL_CREATE,
          content = @Content(schema = @Schema(implementation = DioBranch.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_UNPROCESSABLE_ENTITY,
          description = Labels.RESPONSE_INPUT_VALIDATION_ERROR, content = @Content(
              array = @ArraySchema(schema = @Schema(implementation = ValidationListException.class))))})
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_BRANCH_WRITE)
  default ResponseEntity<DioBranch> createBranch(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @RequestBody DioBranch branch) throws ValidationListException {
    return getDelegate().createBranch(brandId, branch);
  }

  @Operation(summary = "Update a single Branch",
      description = "Updates and Returns an existing Branch",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_BRANCH_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_UPDATE,
          content = @Content(schema = @Schema(implementation = DioBranch.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_UNPROCESSABLE_ENTITY,
          description = Labels.RESPONSE_INPUT_VALIDATION_ERROR, content = @Content(
              array = @ArraySchema(schema = @Schema(implementation = ValidationListException.class))))})
  @PutMapping(path = "/{branchId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_BRANCH_WRITE)
  default ResponseEntity<DioBranch> updateBranch(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("branchId") UUID branchId,
      @NotNull @RequestBody DioBranch branch) throws ValidationListException {
    return getDelegate().updateBranch(brandId, branchId, branch);
  }

  @Operation(summary = "Delete a single Branch", description = "Deletes a Branch",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_BRANCH_WRITE})})

  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_DELETE,
          content = @Content(schema = @Schema(implementation = DioBranch.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NOT_FOUND,
          description = Labels.RESPONSE_OBJECT_NOT_FOUND)})
  @DeleteMapping(path = "/{branchId}")
  @PreAuthorize(Labels.OAUTH2_SCOPE_BRANCH_WRITE)
  default ResponseEntity<Void> deleteBranch(@NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("branchId") UUID branchId) {
    return getDelegate().deleteBranch(brandId, branchId);
  }


}
