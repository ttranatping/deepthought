package io.biza.deepthought.shared.payloads.dio.grant;

import java.util.UUID;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioGrantAccess;
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
@Schema(description = "A single Account Grant")
public class DioGrantAccount {

  @Schema(description = "Account Identifier", required = true)
  @JsonProperty("id")
  UUID accountId;
  
  @Schema(description = "Access Mode", defaultValue = "ALL")
  @JsonProperty("access")
  @Builder.Default
  DioGrantAccess access = DioGrantAccess.ALL;

}
