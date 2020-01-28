package io.biza.deepthought.data.payload;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.babelfish.cdr.v1.model.banking.BankingProductEligibility;
import io.biza.deepthought.data.enumerations.DioSchemeType;
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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "A Deep Thought Product Eligibility Container")
public class DioProductEligibility {

  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Product Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);

  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Scheme Type", defaultValue = "CDR_BANKING")
  @JsonProperty("schemeType")
  public DioSchemeType schemeType;

  @JsonProperty("cdrBanking")
  @Schema(description = "CDR Banking Product Eligibility")
  @Valid
  @NotNull
  @NonNull
  public BankingProductEligibility cdrBanking;

}
