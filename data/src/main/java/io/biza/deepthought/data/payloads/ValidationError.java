package io.biza.deepthought.data.payloads;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.data.enumerations.DioValidationErrorType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Valid
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "A Single Validation Error")
public class ValidationError {
  @JsonProperty("type")
  @NotNull
  @NonNull
  @Schema(description = "Validation Error Type")
  @Builder.Default
  DioValidationErrorType type = DioValidationErrorType.ATTRIBUTE_INVALID;
  
  @JsonProperty("field")
  @Schema(description = "Field which failed validation")
  String field;
  
  @JsonProperty("message")
  @NotNull
  @NonNull
  @Schema(description = "Validation Error Message")
  String message;
}
