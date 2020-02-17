package io.biza.deepthought.data.payloads.dio.common;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.biza.babelfish.cdr.converters.DateTimeStringToOffsetDateTimeConverter;
import io.biza.babelfish.cdr.converters.OffsetDateTimeToDateTimeStringConverter;
import io.biza.deepthought.data.enumerations.DioCustomerType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccount;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
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
@Schema(description = "A Deep Thought Customer Bank Account")
public class DioCustomerBankAccount {
  
  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Customer Bank Account Association Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);
  
  @JsonProperty("customer")
  @ToString.Exclude
  public DioCustomer customer;
  
  @JsonProperty("bankAccount")
  @ToString.Exclude
  public DioBankAccount bankAccount;
  
  @JsonProperty("owner")
  public Boolean owner;
  
}
