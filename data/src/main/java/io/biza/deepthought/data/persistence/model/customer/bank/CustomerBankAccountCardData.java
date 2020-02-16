package io.biza.deepthought.data.persistence.model.customer.bank;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Valid
@Table(name = "CUSTOMER_BANK_ACCOUNT_CARD")
public class CustomerBankAccountCardData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, optional = true)
  @MapsId
  CustomerBankAccountData account;
  
  @Column(name = "ISSUE_DATE_TIME")
  @NotNull
  @CreationTimestamp
  OffsetDateTime issueDateTime;
  
  @Column(name = "CARD_NUMBER")
  @NotNull
  String cardNumber;
  
}
