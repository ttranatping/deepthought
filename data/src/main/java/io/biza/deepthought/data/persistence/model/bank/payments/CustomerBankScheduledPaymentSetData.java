package io.biza.deepthought.data.persistence.model.bank.payments;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.Constants;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@Table(name = "CUSTOMER_SCHEDULED_PAYMENT_SET")
@EqualsAndHashCode
public class CustomerBankScheduledPaymentSetData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @ManyToOne
  @JoinColumn(name = "SCHEDULED_PAYMENT_ID", nullable = false)
  @ToString.Exclude
  CustomerBankScheduledPaymentData scheduledPayment;
  
  @Column(name = "AMOUNT")
  BigDecimal amount;
  
  @Column(name = "CURRENCY")
  @Builder.Default
  Currency currency = Currency.getInstance(Constants.DEFAULT_CURRENCY);
  
  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID")
  @ToString.Exclude
  BankAccountData account;
  
  @ManyToOne
  @JoinColumn(name = "PAYEE_ID")
  @ToString.Exclude
  CustomerBankPayeeData payee;

  
}
