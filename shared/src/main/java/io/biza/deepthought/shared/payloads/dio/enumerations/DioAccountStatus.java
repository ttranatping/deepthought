package io.biza.deepthought.shared.payloads.dio.enumerations;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Account Status", enumAsRef = true)
public enum DioAccountStatus {
  OPEN, CLOSED, FROZEN
}
