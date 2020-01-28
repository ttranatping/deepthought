package io.biza.deepthought.data.enumerations;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Deep Thought Exception Type", enumAsRef = true)
public enum DioValidationErrorType {
  ATTRIBUTE_INVALID, INVALID_JSON, INVALID_FORMAT
}
