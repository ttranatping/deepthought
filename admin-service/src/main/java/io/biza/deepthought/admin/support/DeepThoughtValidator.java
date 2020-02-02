package io.biza.deepthought.admin.support;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.util.StringUtils;
import io.biza.babelfish.cdr.support.AssertTrueBabelfish;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import io.biza.deepthought.data.enumerations.DioValidationErrorType;
import io.biza.deepthought.data.payloads.ValidationError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeepThoughtValidator {
  public static <T> void validate(Validator validator, T input) throws ValidationListException {
    ValidationListException errors = ValidationListException.builder()
        .type(DioExceptionType.VALIDATION_ERROR)
        .explanation("Input has invalid parameters, see validationErrors for explanation").build();

    for (ConstraintViolation<T> error : validator.validate(input)) {
      LOG.debug("Validation experienced and caught: {}", error.getPropertyPath());
      LOG.debug("Validation error class is: {}", error.getConstraintDescriptor());
      if (error.getConstraintDescriptor().getAnnotation() instanceof AssertTrueBabelfish) {
        AssertTrueBabelfish assertion =
            (AssertTrueBabelfish) error.getConstraintDescriptor().getAnnotation();

        errors.validationErrors().add(ValidationError.builder()
            .fields(List.of(assertion.fields()).stream()
                .map(s -> error.getPropertyPath().toString().replaceAll(".[^.]*$", ".") + s)
                .collect(Collectors.toList()))
            .message(StringUtils.capitalize(error.getMessage()))
            .type(DioValidationErrorType.ATTRIBUTE_INVALID).build());

      } else {
        errors.validationErrors()
            .add(ValidationError.builder().fields(List.of(error.getPropertyPath().toString()))
                .message(StringUtils.capitalize(error.getMessage()))
                .type(DioValidationErrorType.ATTRIBUTE_INVALID).build());
      }
    }

    if (errors.validationErrors().size() > 0) {
      throw errors;
    }
  }
}
