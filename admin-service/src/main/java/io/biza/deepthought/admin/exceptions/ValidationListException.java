package io.biza.deepthought.admin.exceptions;

import java.util.List;
import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.shared.payloads.ValidationError;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioExceptionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ValidationListException extends Exception {
  private static final long serialVersionUID = 1L;
  
  @JsonProperty("type")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Exception Type")
  DioExceptionType type;
  
  @JsonProperty("explanation")
  @NotNull
  @NonNull
  @Schema(description = "Validation Exception Explanation")
  String explanation;
  
  @JsonProperty("validationErrors")
  @Schema(description = "A List of Validation Errors Encountered")
  @Builder.Default
  List<ValidationError> validationErrors = new ArrayList<ValidationError>();

}
