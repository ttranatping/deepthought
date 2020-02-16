package io.biza.deepthought.data.persistence.model.account;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
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
import io.biza.deepthought.data.Constants;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.product.ProductBankingData;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Valid
@Table(name = "ACCOUNT_CREDIT_CARD")
public class AccountCreditCardData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @ToString.Exclude
  AccountData account;
  
  @Column(name = "LAST_STATEMENT")
  @NotNull
  @CreationTimestamp
  OffsetDateTime lastStatement;
  
  @Column(name = "PAYMENT_CURRENCY")
  @NotNull
  @Builder.Default
  Currency paymentCurrency = Currency.getInstance(Constants.DEFAULT_CURRENCY);
  
  @Column(name = "MIN_PAYMENT_AMOUNT")
  @NotNull
  BigDecimal paymentMinimum;
  
  @Column(name = "PAYMENT_DUE_AMOUNT")
  @NotNull
  BigDecimal paymentDueAmount;
  
  @Column(name = "PAYMENT_DUE_DATE")
  @NotNull
  OffsetDateTime paymentDue;
  
  @PrePersist
  public void prePersist() {
    if (this.account() != null) {
      Set<AccountCreditCardData> set = new HashSet<AccountCreditCardData>();
      if (this.account().creditCards() != null) {
        set.addAll(this.account().creditCards());
      }
      set.add(this);
      this.account().creditCards(set);
    }
  }

  
}
