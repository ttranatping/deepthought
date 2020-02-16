package io.biza.deepthought.data.payloads.cdr;

import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Valid
@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CDR Person Attributes")
public class CdrCommonPerson {

  @Schema(
      description = "ANZSCO v1.2 Occupation Code",
      type = "string")
  @JsonProperty("occupationCode")
  String occupationCode;

}
