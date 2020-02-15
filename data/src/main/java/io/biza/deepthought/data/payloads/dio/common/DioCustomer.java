package io.biza.deepthought.data.payloads.dio.common;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.data.enumerations.DioCustomerType;
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
@Schema(description = "A Deep Thought Customer Container")
public class DioCustomer {

  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Customer Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);
  
  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Scheme Type", defaultValue = "DIO_COMMON")
  @Builder.Default
  public DioSchemeType schemeType = DioSchemeType.DIO_COMMON;

  @JsonProperty("customerType")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Customer Type")
  public DioCustomerType customerType;

  @JsonProperty("person")
  @Schema(description = "Deep Thought Person")
  DioPerson person;
  
  @JsonProperty("organisation")
  @Schema(description = "Deep Thought Organisation")
  DioOrganisation organisation;
  
}
