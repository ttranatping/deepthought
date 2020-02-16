package io.biza.deepthought.data.payloads.dio;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
@Schema(description = "A single brand")
public class DioBrand {

  @Schema(description = "Brand Identifier", required = true)
  @JsonProperty("id")
  public UUID id;

  @Schema(description = "Brand Name", required = true)
  @JsonProperty("name")
  @NotNull
  @NonNull
  public String name;

  @Schema(description = "Brand Display Name", required = true)
  @JsonProperty("displayName")
  @NotNull
  @NonNull
  public String displayName;

}
