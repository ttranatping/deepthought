package io.biza.deepthought.admin.exceptions;

import javax.validation.Valid;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Valid
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class AttributeRemovedFromPayloadException extends Exception {
  private static final long serialVersionUID = 1L;
  private DioExceptionType code;

  public AttributeRemovedFromPayloadException(DioExceptionType code) {
    super();
    this.code = code;
  }

}
