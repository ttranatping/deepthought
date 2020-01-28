package io.biza.deepthought.admin.api;

import io.biza.deepthought.admin.Labels;
import io.biza.deepthought.admin.api.delegate.ProductBundleAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payload.DioProductBundle;
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

@Tag(name = Labels.TAG_PRODUCT_BUNDLE_NAME, description = Labels.TAG_PRODUCT_BUNDLE_DESCRIPTION)
@RequestMapping("/v1/brand/{brandId}/bundle")
public interface ProductBundleAdminApi {

  default ProductBundleAdminApiDelegate getDelegate() {
    return new ProductBundleAdminApiDelegate() {};
  }

  @Operation(summary = "List all Product Bundles", description = "List all Product Bundles",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_READ})})
  @ApiResponses(value = {@ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
      description = Labels.RESPONSE_SUCCESSFUL_LIST, content = @Content(
          array = @ArraySchema(schema = @Schema(implementation = DioProductBundle.class))))})
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_READ)
  default ResponseEntity<List<DioProductBundle>> listProductBundles(
      @NotNull @Valid @PathVariable("brandId") UUID brandId) {
    return getDelegate().listProductBundles(brandId);
  }

  @Operation(summary = "Get a single Product Bundle",
      description = "Returns a single product bundle entry",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_READ})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_READ,
          content = @Content(schema = @Schema(implementation = DioProductBundle.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NOT_FOUND,
          description = Labels.RESPONSE_OBJECT_NOT_FOUND)})
  @GetMapping(value = "/{bundleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_READ)
  default ResponseEntity<DioProductBundle> getProductBundle(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("bundleId") UUID bundleId) {
    return getDelegate().getProductBundle(brandId, bundleId);
  }

  @Operation(summary = "Create a Product Bundle",
      description = "Creates a new Product Bundle and returns it",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_CREATED,
          description = Labels.RESPONSE_SUCCESSFUL_LIST,
          content = @Content(schema = @Schema(implementation = DioProductBundle.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_UNPROCESSABLE_ENTITY,
          description = Labels.RESPONSE_INPUT_VALIDATION_ERROR, content = @Content(
              array = @ArraySchema(schema = @Schema(implementation = ValidationListException.class))))})
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_WRITE)
  default ResponseEntity<DioProductBundle> createProductBundle(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @RequestBody DioProductBundle bundle) throws ValidationListException {
    return getDelegate().createProductBundle(brandId, bundle);
  }

  @Operation(summary = "Update a single Product Bundle",
      description = "Updates existing Product Bundle and returns it",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_LIST,
          content = @Content(schema = @Schema(implementation = DioProductBundle.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_UNPROCESSABLE_ENTITY,
          description = Labels.RESPONSE_INPUT_VALIDATION_ERROR, content = @Content(
              array = @ArraySchema(schema = @Schema(implementation = ValidationListException.class))))})
  @PutMapping(path = "/{bundleId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_WRITE)
  default ResponseEntity<DioProductBundle> updateProductBundle(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("bundleId") UUID bundleId,
      @NotNull @RequestBody DioProductBundle bundle) throws ValidationListException {
    return getDelegate().updateProductBundle(brandId, bundleId, bundle);
  }

  @Operation(summary = "Delete a single Product Bundle", description = "Deletes a Product Bundle",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_WRITE})})

  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NO_CONTENT,
          description = Labels.RESPONSE_SUCCESSFUL_LIST),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NOT_FOUND,
          description = Labels.RESPONSE_OBJECT_NOT_FOUND)})
  @DeleteMapping(path = "/{bundleId}")
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_WRITE)
  default ResponseEntity<Void> deleteProductBundle(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("bundleId") UUID bundleId) {
    return getDelegate().deleteProductBundle(brandId, bundleId);
  }


}