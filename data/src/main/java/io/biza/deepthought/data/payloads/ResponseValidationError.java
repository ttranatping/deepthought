package io.biza.deepthought.data.payloads;

import java.util.List;
import java.util.ArrayList;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.data.enumerations.DioExceptionType;
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
@Schema(description = "Validation Error Response")
public class ResponseValidationError {
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
