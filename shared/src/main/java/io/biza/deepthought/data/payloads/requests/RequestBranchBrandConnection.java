package io.biza.deepthought.data.payloads.requests;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Valid
@ToString
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request containing Branch identifier to associated with a Brand")
public class RequestBranchBrandConnection {
  @Schema(required = true)
  @JsonProperty("branchId")
  @NotNull
  @Valid
  UUID branchId;
  
}
