package io.biza.deepthought.data.persistence.model.account;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioAccountStatus;
import io.biza.deepthought.data.enumerations.DioBankAccountType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.bank.BranchData;
import io.biza.deepthought.data.persistence.model.customer.CustomerAccountData;
import io.biza.deepthought.data.persistence.model.payments.ScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.product.ProductBankingCardArtData;
import io.biza.deepthought.data.persistence.model.product.ProductBankingConstraintData;
import io.biza.deepthought.data.persistence.model.product.ProductBankingEligibilityData;
import io.biza.deepthought.data.persistence.model.product.ProductBankingFeatureData;
import io.biza.deepthought.data.persistence.model.product.ProductBankingFeeData;
import io.biza.deepthought.data.persistence.model.product.ProductBankingRateDepositData;
import io.biza.deepthought.data.persistence.model.product.ProductBankingRateLendingData;
import io.biza.deepthought.data.persistence.model.product.ProductBundleData;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Parameter;
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
@Table(name = "ACCOUNT")
@EqualsAndHashCode
public class AccountData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_BANKING;

  /**
   * Product Definition
   */
  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID", nullable = false)
  ProductData product;

  /**
   * Bundle Definition
   */
  @ManyToOne
  @JoinColumn(name = "BUNDLE_ID", nullable = false)
  ProductBundleData bundle;
  
  /**
   * Customer access to Account
   */
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<CustomerAccountData> customerAccounts;
  
  /**
   * Account Number
   */
  @Column(name = "ACCOUNT_NUMBER")
  @GenericGenerator(name = "accountIdGenerator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {@Parameter(name = "sequence_name", value = "accountNumber_sequence"),
          @Parameter(name = "initial_value", value = "100000"),
          @Parameter(name = "increment_size", value = "1")})
  @GeneratedValue(generator = "accountIdGenerator")
  Integer accountNumber;

  /**
   * Account Opening Date
   */
  @Column(name = "CREATION_DATE_TIME")
  OffsetDateTime creationDateTime;
  
  /**
   * Account Closure Date
   */
  @Column(name = "CLOSED_DATE_TIME")
  OffsetDateTime closedDate;
  
  /**
   * Account Status
   */
  @Column(name = "STATUS")
  DioAccountStatus status;

  /**
   * Display Name
   */
  @Column(name = "DISPLAY_NAME")
  @NotNull
  String displayName;

  /**
   * Nick Name
   */
  @Column(name = "NICK_NAME")
  String nickName;

  /**
   * Branch Details
   */
  @ManyToOne
  @JoinColumn(name = "BRANCH_ID", nullable = false)
  @ToString.Exclude
  @NotNull
  BranchData branch;

  /**
   * Account Type
   */
  @Column(name = "ACCOUNT_TYPE", nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull
  DioBankAccountType accountType;

  /**
   * Term Deposit details
   */
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<AccountTermDepositData> termDeposits;

  /**
   * Loan Account details
   */
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  @Size(min = 0, max = 1, message = "Only one loan per account supported at this time")
  Set<AccountLoanAccountData> loanAccounts;
  
  /**
   * Credit Card details
   */
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  @Size(min = 0, max = 1, message = "Only one credit card detail supported at this time")
  Set<AccountCreditCardData> creditCards;
  
  /**
   * Features and activation state
   */
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<AccountFeatureData> features;
  
  /**
   * Scheduled Payments
   */
  @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<ScheduledPaymentData> scheduledPayments;
  
}
