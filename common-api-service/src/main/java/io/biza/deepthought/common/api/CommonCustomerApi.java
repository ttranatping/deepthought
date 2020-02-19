package io.biza.deepthought.common.api;

import io.biza.babelfish.cdr.models.responses.ResponseCommonCustomerDetailV1;
import io.biza.babelfish.cdr.models.responses.ResponseCommonCustomerV1;
import io.biza.deepthought.common.api.delegate.CommonCustomerApiDelegate;
import io.biza.deepthought.shared.support.CDRConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tags({
  @Tag(name = CDRConstants.TAG_COMMON_NAME, description = CDRConstants.TAG_COMMON_DESCRIPTION),
  @Tag(name = CDRConstants.TAG_CUSTOMER_NAME, description = CDRConstants.TAG_CUSTOMER_DESCRIPTION)
})
@RequestMapping("/v1/common/customer")
public interface CommonCustomerApi {

  default CommonCustomerApiDelegate getDelegate() {
    return new CommonCustomerApiDelegate() {};
  }

  @Operation(summary = "Get Customer",
      description = "Obtain basic information on the customer that has authorised the current session")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ,
      content = @Content(schema = @Schema(implementation = ResponseCommonCustomerV1.class)))})
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_CUSTOMER_BASIC_READ)
  default ResponseEntity<ResponseCommonCustomerV1> getCustomer() {
    return getDelegate().getCustomer();
  }

  @Operation(summary = "Get Customer Detail",
      description = "Obtain detailed information on the authorised customer within the current session.")
  @ApiResponses(value = {@ApiResponse(responseCode = CDRConstants.RESPONSE_CODE_OK,
      description = CDRConstants.RESPONSE_SUCCESSFUL_READ,
      content = @Content(schema = @Schema(implementation = ResponseCommonCustomerDetailV1.class)))})
  @GetMapping(value = "/detail", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(CDRConstants.OAUTH2_SCOPE_CUSTOMER_DETAIL_READ)
  default ResponseEntity<ResponseCommonCustomerDetailV1> getCustomerDetail() {
    return getDelegate().getCustomerDetail();
  }
}

