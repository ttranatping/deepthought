package io.biza.deepthought.shared.payloads.requests;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.shared.payloads.dio.common.DioCustomerAccount;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioGrantAccess;
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
@Schema(description = "Grant Bank Account with Permissions")
public class RequestGrantCustomerAccount {
  
  @Schema(description = "Customer Account Details")
  @JsonProperty("customerAccountId")
  @NotNull
  UUID customerAccountId;
  
  @Schema(description = "Granted Permissions")
  @JsonProperty("permissions")
  List<DioGrantAccess> permissions;

}
