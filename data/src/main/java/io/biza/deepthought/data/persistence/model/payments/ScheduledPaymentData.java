package io.biza.deepthought.data.persistence.model.payments;

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
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.BankingScheduledPaymentStatus;
import io.biza.deepthought.data.persistence.model.account.AccountData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
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
@Table(name = "ACCOUNT")
@EqualsAndHashCode
public class ScheduledPaymentData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
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
  AccountData from;
  
  @OneToMany(mappedBy = "scheduledPayment", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<ScheduledPaymentSetData> paymentSet;

}
