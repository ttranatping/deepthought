package io.biza.deepthought.data.payloads.dio.product;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductCardArtV1;
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
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A Deep Thought Product Card Art Container")
public class DioProductCardArt {

  @JsonProperty("id")
  @Schema(description = "Deep Thought Card Art Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  public UUID id;


  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Scheme Type", defaultValue = "CDR_BANKING")
  public DioSchemeType schemeType;


  @JsonProperty("cdrBanking")
  @Schema(description = "CDR Banking Card Art")
  @Valid
  @NotNull
  @NonNull
  public BankingProductCardArtV1 cdrBanking;

}
