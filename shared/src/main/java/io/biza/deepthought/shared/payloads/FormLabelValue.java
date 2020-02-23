package io.biza.deepthought.shared.payloads;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Valid
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "FormLabelValue", description = "Form Label to Value Mapping")
public class FormLabelValue {

  @JsonProperty("label")
  @Schema(description = "Form Label", example = "Form Label")
  @NotNull
  public String label;

  @JsonProperty("value")
  @Schema(description = "Form Value", example = "Form Value")
  @NotNull
  public String value;

}
