package io.biza.deepthought.data.enumerations;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Account Status", enumAsRef = true)
public enum DioAccountStatus {
  OPEN, CLOSED, FROZEN
}
