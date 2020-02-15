package io.biza.deepthought.data.payloads.dio.common;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.babelfish.cdr.enumerations.AddressPurpose;
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
@Schema(description = "A Deep Thought Physical Address Container")
public class DioAddress {

  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Address Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  UUID id = new UUID(0, 0);

  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Scheme Type", defaultValue = "DIO_COMMON")
  @Builder.Default
  DioSchemeType schemeType = DioSchemeType.DIO_COMMON;
  
  @JsonProperty("purpose")
  @NotNull
  @Schema(description = "Address Purpose")
  AddressPurpose purpose;

  @JsonProperty("simple")
  @Schema(description = "Simple Address")
  DioAddressSimple simple;
  
  @JsonProperty("paf")
  @Schema(description = "PAF Address")
  DioAddressPAF paf;

}
