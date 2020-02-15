package io.biza.deepthought.data.persistence.model.account;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.BankingTermDepositMaturityInstructions;
import io.biza.babelfish.cdr.enumerations.CommonOrganisationType;
import io.biza.deepthought.data.enumerations.DioLoanRepaymentType;
import io.biza.deepthought.data.persistence.converter.URIDataConverter;
import io.biza.deepthought.data.persistence.model.product.ProductBankingFeatureData;
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
