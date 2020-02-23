package io.biza.deepthought.data.enumerations;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Deep Thought Exception Type", enumAsRef = true)
public enum DioExceptionType {
  INVALID_BRAND, INVALID_BRANCH, INVALID_JSON, INVALID_PARAMETER_FORMAT,
  VALIDATION_ERROR, INVALID_BRAND_AND_PRODUCT, UNSUPPORTED_PRODUCT_SCHEME_TYPE, DATABASE_ERROR, INVALID_PRODUCT, INVALID_BUNDLE, INVALID_CUSTOMER, INVALID_ACCOUNT, INVALID_ASSOCIATION
}
