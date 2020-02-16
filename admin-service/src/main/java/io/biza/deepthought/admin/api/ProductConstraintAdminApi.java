package io.biza.deepthought.admin.api;

import io.biza.deepthought.admin.Labels;
import io.biza.deepthought.admin.api.delegate.ProductConstraintAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.product.DioBankProductConstraint;
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

@Tag(name = Labels.TAG_PRODUCT_NAME, description = Labels.TAG_PRODUCT_DESCRIPTION)
@RequestMapping("/v1/brand/{brandId}/product/{productId}/constraint")
public interface ProductConstraintAdminApi {

  default ProductConstraintAdminApiDelegate getDelegate() {
    return new ProductConstraintAdminApiDelegate() {};
  }

  @Operation(summary = "List all Product Constraints", description = "List all Product Constraints",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME, 
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_READ})})
  @ApiResponses(value = {@ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
      description = Labels.RESPONSE_SUCCESSFUL_LIST, content = @Content(
          array = @ArraySchema(schema = @Schema(implementation = DioBankProductConstraint.class))))})
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_READ)
  default ResponseEntity<List<DioBankProductConstraint>> listProductConstraints(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("productId") UUID productId) {
    return getDelegate().listProductConstraints(brandId, productId);
  }

  @Operation(summary = "Get a single Product Constraint",
      description = "Returns a single product constraint entry",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_READ})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_READ,
          content = @Content(schema = @Schema(implementation = DioBankProductConstraint.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NOT_FOUND,
          description = Labels.RESPONSE_OBJECT_NOT_FOUND)})
  @GetMapping(value = "/{constraintId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_READ)
  default ResponseEntity<DioBankProductConstraint> getProductConstraint(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("productId") UUID productId,
      @NotNull @Valid @PathVariable("constraintId") UUID constraintId) {
    return getDelegate().getProductConstraint(brandId, productId, constraintId);
  }

  @Operation(summary = "Create a Product Constraint",
      description = "Creates and Returns a new Product Constraint",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_CREATE,
          content = @Content(schema = @Schema(implementation = DioBankProductConstraint.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_UNPROCESSABLE_ENTITY,
          description = Labels.RESPONSE_INPUT_VALIDATION_ERROR, content = @Content(
              array = @ArraySchema(schema = @Schema(implementation = ValidationListException.class))))})
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_WRITE)
  default ResponseEntity<DioBankProductConstraint> createProductConstraint(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("productId") UUID productId,
      @NotNull @RequestBody DioBankProductConstraint constraint) throws ValidationListException {
    return getDelegate().createProductConstraint(brandId, productId, constraint);
  }

  @Operation(summary = "Update a single Product Constraint",
      description = "Updates and Returns an existing Product Constraint",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_UPDATE,
          content = @Content(schema = @Schema(implementation = DioBankProductConstraint.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_UNPROCESSABLE_ENTITY,
          description = Labels.RESPONSE_INPUT_VALIDATION_ERROR, content = @Content(
              array = @ArraySchema(schema = @Schema(implementation = ValidationListException.class))))})
  @PutMapping(path = "/{constraintId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_WRITE)
  default ResponseEntity<DioBankProductConstraint> updateProductConstraint(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("productId") UUID productId,
      @NotNull @Valid @PathVariable("constraintId") UUID constraintId,
      @NotNull @RequestBody DioBankProductConstraint constraint) throws ValidationListException {
    return getDelegate().updateProductConstraint(brandId, productId, constraintId, constraint);
  }

  @Operation(summary = "Delete a single Product Constraint",
      description = "Deletes a Product Constraint",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NO_CONTENT,
          description = Labels.RESPONSE_SUCCESSFUL_DELETE),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NOT_FOUND,
          description = Labels.RESPONSE_OBJECT_NOT_FOUND)})
  @DeleteMapping(path = "/{constraintId}")
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_WRITE)
  default ResponseEntity<Void> deleteProductConstraint(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("productId") UUID productId,
      @NotNull @Valid @PathVariable("constraintId") UUID constraintId) {
    return getDelegate().deleteProductConstraint(brandId, productId, constraintId);
  }


}
