package io.biza.deepthought.data.persistence.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import io.biza.babelfish.cdr.enumerations.BankingAccountStatus;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.enumerations.CommonOrganisationType;
import io.biza.deepthought.data.enumerations.DioCustomerType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateLendingTierApplicabilityData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@Table(name = "ACCOUNT")
@EqualsAndHashCode
public class AccountData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<CustomerAccountData> customers;
  
  @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<ScheduledPaymentData> scheduledPayment;
  
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<ScheduledPaymentSetData> scheduledPaymentSet;
  
  @Column(name = "CREATION_DATE")
  LocalDate creationDate;
  
  @Column(name = "DISPLAY_NAME")
  @NotNull
  String displayName;
  
  @Column(name = "NICK_NAME")
  String nickName;
  
  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  BankingAccountStatus status;

  @ManyToOne
  @JoinColumn(name = "BRAND_BRANCH_ID", nullable = false)
  @ToString.Exclude
  @NotNull
  BranchData branch;
  
  @Column(name = "ACCOUNT_NUMBER")
  String accountNumber;
  
  @Column(name = "PRODUCT_CATEGORY", nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull
  BankingProductCategory productCategory;
  
  @Column(name = "PRODUCT_NAME", nullable = false)
  @NotNull
  String productName;
  
  @Column(name = "BUNDLE_NAME")
  String bundleName;
  
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<AccountTermDepositData> termDeposits;
  
  @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, optional = true)
  AccountLoanAccountData loanAccount;
  
  @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, optional = true)
  AccountCreditCardAccountData creditCard;

  @Column(name = "DEPOSIT_RATE")
  BigDecimal depositRate;
  
  @Column(name = "LENDING_RATE")
  BigDecimal lendingRate;
  
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<AccountRateDepositData> depositRates;
  
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<AccountRateLendingData> lendingRates;
  
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<AccountFeatureData> features;
  
}
