package io.biza.deepthought.shared.payloads.dio.banking;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioSchemeType;
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
@Schema(description = "A Deep Thought Bank Account Container")
public class DioBankAuthorisedEntity {

  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Bank Account Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);

  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Scheme Type", defaultValue = "DIO_BANKING")
  @Builder.Default
  public DioSchemeType schemeType = DioSchemeType.DIO_BANKING;

  @Schema(
      description = "Description of the authorised entity derived from previously executed direct debits")
  @JsonProperty("description")
  String description;

  @JsonProperty("branch")
  DioBankBranch branch;

  @Schema(description = "Australian Business Number for the authorised entity")
  @JsonProperty("abn")
  String abn;

  @Schema(description = "Australian Company Number for the authorised entity")
  @JsonProperty("acn")
  String acn;

  @Schema(description = "Australian Registered Body Number for the authorised entity")
  @JsonProperty("arbn")
  String arbn;

}
