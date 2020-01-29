package io.biza.deepthought.admin.support;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.util.StringUtils;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import io.biza.deepthought.data.enumerations.DioValidationErrorType;
import io.biza.deepthought.data.payloads.ValidationError;

public class DeepThoughtValidator {
   public static <T> void validate(Validator validator, T input) throws ValidationListException {
     ValidationListException errors = ValidationListException.builder()
         .type(DioExceptionType.VALIDATION_ERROR)
         .explanation("Input has invalid parameters, see validationErrors for explanation").build();

     for(ConstraintViolation<T> error : validator.validate(input)) {
           errors.validationErrors()
               .add(ValidationError.builder().field(error.getPropertyPath().toString())
                   .message(StringUtils.capitalize(error.getMessage()))
                   .type(DioValidationErrorType.ATTRIBUTE_INVALID).build());
     }
     
     if(errors.validationErrors().size() > 0) {
       throw errors;
     }
   }
}
