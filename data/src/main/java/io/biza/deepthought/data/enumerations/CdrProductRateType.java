package io.biza.deepthought.data.enumerations;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "CDR Product Rate Type", enumAsRef = true)
public enum CdrProductRateType {
  DEPOSIT, LENDING
}
