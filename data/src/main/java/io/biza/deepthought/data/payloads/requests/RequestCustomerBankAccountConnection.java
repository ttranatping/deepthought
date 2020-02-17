package io.biza.deepthought.data.payloads.requests;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.babelfish.cdr.models.payloads.MetaV1;
import io.biza.babelfish.cdr.models.payloads.common.AccountIdsListV1;
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
@Schema(description = "Request containing Account Identifier to link to a Customer")
public class RequestCustomerBankAccountConnection {
  @Schema(required = true)
  @JsonProperty("bankAccountId")
  @NotNull
  @Valid
  UUID bankAccountId;
  
  @Schema(required = true)
  @JsonProperty("makeOwner")
  @NotNull
  @Valid
  @Builder.Default
  Boolean makeOwner = true;
  
}
