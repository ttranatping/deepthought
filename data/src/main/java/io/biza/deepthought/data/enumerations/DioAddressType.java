package io.biza.deepthought.data.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import io.biza.babelfish.cdr.enumerations.BankingProductConstraintType;
import io.biza.babelfish.cdr.exceptions.LabelValueEnumValueNotSupportedException;
import io.biza.babelfish.cdr.support.LabelValueEnumInterface;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Address Type", enumAsRef = true)
public enum DioAddressType implements LabelValueEnumInterface {
  // @formatter:off
  SIMPLE("SIMPLE", "Simple Address"),
  PAF("PAF", "AusPost Postal Address Format");
  // @formatter:on

  private String value;

  private String label;

  DioAddressType(String value, String label) {
    this.value = value;
    this.label = label;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static DioAddressType fromValue(String text)
      throws LabelValueEnumValueNotSupportedException {
    for (DioAddressType b : DioAddressType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    throw new LabelValueEnumValueNotSupportedException(
        "Unable to identify value of DioAddressType from " + text,
        DioAddressType.class.getSimpleName(), DioAddressType.values(),
        text);
  }

  @Override
  @JsonIgnore
  public String label() {
    return label;
  }
}