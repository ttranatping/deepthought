package io.biza.deepthought.data.persistence.model.bank.account;

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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioAccountStatus;
import io.biza.deepthought.data.enumerations.DioBankAccountType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.bank.BankBranchData;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.customer.bank.CustomerBankAccountData;
import io.biza.deepthought.data.persistence.model.product.ProductBundleData;
import io.biza.deepthought.data.persistence.model.product.ProductData;
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
@Table(name = "BANK_ACCOUNT")
@EqualsAndHashCode
public class BankAccountData {

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
  @JoinColumn(name = "BUNDLE_ID", nullable = true)
  ProductBundleData bundle;
  
  /**
   * Customer access to Account
   */
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<CustomerBankAccountData> customerAccounts;
  
  /**
   * Account Number
   */
  @Column(name = "ACCOUNT_NUMBER", unique = true)
  @NotNull
  Integer accountNumber;

  /**
   * Account Opening Date
   */
  @Column(name = "CREATION_DATE_TIME")
  @CreationTimestamp
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
  BankBranchData branch;

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
  Set<BankAccountTermDepositData> termDeposits;

  /**
   * Loan Account details
   */
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  @Size(min = 0, max = 1, message = "Only one loan per account supported at this time")
  Set<BankAccountLoanAccountData> loanAccounts;
  
  /**
   * Credit Card details
   */
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  @Size(min = 0, max = 1, message = "Only one credit card detail supported at this time")
  Set<BankAccountCreditCardData> creditCards;
  
  /**
   * Features and activation state
   */
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<BankAccountFeatureData> features;
  
  /**
   * Scheduled Payments
   */
  @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<CustomerBankScheduledPaymentData> scheduledPayments;
  
  @PrePersist
  public void prePersist() {
    if(this.branch() != null) {
      Set<BankAccountData> set = new HashSet<BankAccountData>();
      if (this.branch().accounts() != null) {
        set.addAll(this.branch().accounts());
      }
      set.add(this);
      this.branch().accounts(set);
    }
    if (this.product() != null) {
      Set<BankAccountData> set = new HashSet<BankAccountData>();
      if (this.product().accounts() != null) {
        set.addAll(this.product().accounts());
      }
      set.add(this);
      this.product().accounts(set);
    }
    
    if(this.bundle() != null) {
      Set<BankAccountData> set = new HashSet<BankAccountData>();
      if (this.bundle().accounts() != null) {
        set.addAll(this.bundle().accounts());
      }
      set.add(this);
      this.bundle().accounts(set);
    }
  }
  
}
