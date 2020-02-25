package io.biza.deepthought.shared.payloads.dio.enumerations;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Scheme Type", enumAsRef = true)
public enum DioSchemeType {
  CDR_BANKING, DIO_BANKING, CDR_COMMON, DIO_COMMON
}
