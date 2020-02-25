package io.biza.deepthought.shared.payloads.dio.enumerations;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Deep Thought Exception Type", enumAsRef = true)
public enum DioValidationErrorType {
  ATTRIBUTE_INVALID, INVALID_JSON, INVALID_FORMAT, DATABASE_ERROR
}
