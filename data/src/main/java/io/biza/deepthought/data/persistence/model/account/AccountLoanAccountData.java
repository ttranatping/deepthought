package io.biza.deepthought.data.persistence.model.account;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.Period;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioLoanRepaymentType;
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
@Table(name = "ACCOUNT_LOAN_ACCOUNT")
public class AccountLoanAccountData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @ToString.Exclude
  private AccountData account;
  
  @Column(name = "CREATION_DATETIME")
  @NotNull
  @CreationTimestamp
  OffsetDateTime creationDateTime;
  
  @Column(name = "CREATION_AMOUNT")
  @NotNull
  BigDecimal creationAmount;
  
  @Column(name = "CURRENCY")
  @Builder.Default
  Currency currency = Currency.getInstance("AUD");
  
  @Column(name = "CREATION_LENGTH")
  Period creationLength;
  
  @Column(name = "REPAYMENT_FREQUENCY")
  Period repaymentFrequency;
  
  @Column(name = "NEXT_REPAYMENT")
  OffsetDateTime lastRepayment;
  
  @Column(name = "NEXT_REPAYMENT_AMOUNT")
  BigDecimal nextRepaymentAmount;
  
  @Column(name = "REDRAW_AVAILABLE")
  BigDecimal redrawAvailable;
  
  @Column(name = "REPAYMENT_TYPE")
  DioLoanRepaymentType repaymentType;
  
}
