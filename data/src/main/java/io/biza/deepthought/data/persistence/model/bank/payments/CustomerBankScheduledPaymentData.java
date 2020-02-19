package io.biza.deepthought.data.persistence.model.bank.payments;

import java.time.OffsetDateTime;
import java.time.Period;
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
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.BankingPaymentNonBusinessDayTreatment;
import io.biza.babelfish.cdr.enumerations.BankingScheduledPaymentStatus;
import io.biza.babelfish.cdr.enumerations.CommonWeekDay;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingScheduledPaymentRecurrence;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
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
@Table(name = "CUSTOMER_SCHEDULED_PAYMENT")
public class CustomerBankScheduledPaymentData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.CDR_BANKING;
  
  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID", nullable = false)
  @ToString.Exclude
  CustomerData customer;
  
  @Column(name = "NICK_NAME")
  String nickName;
  
  @Column(name = "PAYER_REFERENCE")
  @NotNull
  @Builder.Default
  String payerReference = "";
  
  @Column(name = "PAYEE_REFERENCE")
  @NotNull
  @Builder.Default
  String payeeReference = "";
  
  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  BankingScheduledPaymentStatus status;
  
  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @ToString.Exclude
  BankAccountData from;
  
  @OneToMany(mappedBy = "scheduledPayment", cascade = CascadeType.ALL)
  Set<CustomerBankScheduledPaymentSetData> paymentSet;
  
  @Column(name = "NEXT_PAYMENT_DATE")
  OffsetDateTime nextPaymentDate;
  
  @Column(name = "SCHEDULE_TYPE")
  @Enumerated(EnumType.STRING)
  PayloadTypeBankingScheduledPaymentRecurrence scheduleType;
  
  @Column(name = "FINAL_PAYMENT_DATE")
  OffsetDateTime finalPaymentDate;
  
  @Column(name = "PAYMENTS_REMAINING")
  Integer paymentsRemaining;
  
  @Column(name = "NON_BUSINESS_DAY_TREATMENT")
  @Enumerated(EnumType.STRING)
  BankingPaymentNonBusinessDayTreatment nonBusinessDayTreatment;
  
  @Column(name = "PAYMENT_FREQUENCY")
  Period paymentFrequency;
  
  @Column(name = "DAY_OF_WEEK")
  @Enumerated(EnumType.STRING)
  CommonWeekDay dayOfWeek;
  
  @Column(name = "SCHEDULE_DESCRIPTION")
  String scheduleDescription;
  
  @PrePersist
  public void prePersist() {
    if(this.paymentSet() != null) {
      for(CustomerBankScheduledPaymentSetData item : this.paymentSet() ) {
        item.scheduledPayment(this);
      }
    }
  }

}
