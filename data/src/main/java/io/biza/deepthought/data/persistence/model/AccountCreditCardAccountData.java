package io.biza.deepthought.data.persistence.model;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Set;
import java.util.Currency;
import java.util.HashSet;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.BankingTermDepositMaturityInstructions;
import io.biza.babelfish.cdr.enumerations.CommonOrganisationType;
import io.biza.deepthought.data.persistence.converter.URIDataConverter;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeatureData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
@Table(name = "ACCOUNT_CREDIT_CARD")
public class AccountCreditCardAccountData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ACCOUNT_ID")
  @ToString.Exclude
  private AccountData account;
  
  @Column(name = "MIN_PAYMENT_AMOUNT")
  @NotNull
  BigDecimal minimumPaymentAmount;
  
  @Column(name = "PAYMENT_DUE_AMOUNT")
  @NotNull
  BigDecimal paymentDueAmount;
  
  @Column(name = "PAYMENT_CURRENCY")
  @NotNull
  @Builder.Default
  Currency paymentCurrency = Currency.getInstance("AUD");
  
  @Column(name = "PAYMENT_DUE_DATE")
  @NotNull
  LocalDate paymentDueDate;
  
}
