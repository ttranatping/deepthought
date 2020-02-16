package io.biza.deepthought.data.payloads.dio.banking;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.BankingPayeeDetailV1;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payloads.cdr.CdrBankingProduct;
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
@Schema(description = "A Deep Thought Product Container")
public class DioPayee {

  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Product Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);

  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Scheme Type", defaultValue = "CDR_BANKING")
  @Builder.Default
  public DioSchemeType schemeType = DioSchemeType.CDR_BANKING;

  @JsonProperty("cdrBanking")
  @Schema(description = "CDR Banking Product")
  @Valid
  @NotNull
  @NonNull
  public BankingPayeeDetailV1 cdrBanking;

}
