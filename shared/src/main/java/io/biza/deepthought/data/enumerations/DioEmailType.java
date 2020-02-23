package io.biza.deepthought.data.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import io.biza.babelfish.cdr.exceptions.LabelValueEnumValueNotSupportedException;
import io.biza.babelfish.cdr.support.LabelValueEnumInterface;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Email Address Type", enumAsRef = true)
public enum DioEmailType implements LabelValueEnumInterface {
  // @formatter:off
  HOME("HOME", "Home Number"),
  WORK("WORK", "Work Number"),
  OTHER("OTHER", "Other Number");
  // @formatter:on

  private String value;

  private String label;

  DioEmailType(String value, String label) {
    this.value = value;
    this.label = label;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static DioEmailType fromValue(String text)
      throws LabelValueEnumValueNotSupportedException {
    for (DioEmailType b : DioEmailType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    throw new LabelValueEnumValueNotSupportedException(
        "Unable to identify value of DioEmailType from " + text,
        DioEmailType.class.getSimpleName(), DioEmailType.values(),
        text);
  }

  @Override
  @JsonIgnore
  public String label() {
    return label;
  }
}