package io.biza.deepthought.data.persistence.model.account;

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
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ACCOUNT_ID")
  @ToString.Exclude
  private AccountData account;
  
  @Column(name = "ORIGINAL_START_DATE")
  LocalDate originalStartDate;
  
  @Column(name = "ORIGINAL_LOAN_AMOUNT")
  BigDecimal originalLoanAmount;
  
  @Column(name = "ORIGINAL_LOAN_CURRENCY")
  @Builder.Default
  Currency originalLoanCurrency = Currency.getInstance("AUD");
  
  @Column(name = "LOAN_END_DATE", nullable = false)
  LocalDate loanEndDate;
  
  @Column(name = "NEXT_INSTALMENT_DATE", nullable = false)
  @NotNull
  LocalDate nextInstalmentDate;
    
  @Column(name = "MIN_INSTALMENT_AMOUNT")
  BigDecimal minInstalmentAmount;
  
  @Column(name = "MIN_INSTALMENT_CURRENCY")
  @Builder.Default
  Currency minInstalmentCurrency = Currency.getInstance("AUD");
  
  @Column(name = "MAX_REDRAW_AMOUNT")
  BigDecimal maxRedraw;
  
  @Column(name = "MAX_REDRAW_CURRENCY")
  @Builder.Default
  Currency maxRedrawCurrency = Currency.getInstance("AUD");
  
  @Column(name = "MIN_REDRAW_AMOUNT")
  BigDecimal minRedraw;
  
  @Column(name = "MIN_REDRAW_CURRENCY")
  @Builder.Default
  Currency minRedrawCurrency = Currency.getInstance("AUD");
  
}
