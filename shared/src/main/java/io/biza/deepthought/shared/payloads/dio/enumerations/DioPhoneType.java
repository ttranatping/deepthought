package io.biza.deepthought.shared.payloads.dio.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import io.biza.babelfish.cdr.exceptions.LabelValueEnumValueNotSupportedException;
import io.biza.babelfish.cdr.support.LabelValueEnumInterface;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Phone Number Type", enumAsRef = true)
public enum DioPhoneType implements LabelValueEnumInterface {
  // @formatter:off
  MOBILE("MOBILE", "Mobile Number"),
  HOME("HOME", "Home Number"),
  WORK("WORK", "Work Number"),
  OTHER("OTHER", "Other Number");
  // @formatter:on

  private String value;

  private String label;

  DioPhoneType(String value, String label) {
    this.value = value;
    this.label = label;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static DioPhoneType fromValue(String text)
      throws LabelValueEnumValueNotSupportedException {
    for (DioPhoneType b : DioPhoneType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    throw new LabelValueEnumValueNotSupportedException(
        "Unable to identify value of DioPhoneType from " + text,
        DioPhoneType.class.getSimpleName(), DioPhoneType.values(),
        text);
  }

  @Override
  @JsonIgnore
  public String label() {
    return label;
  }
}