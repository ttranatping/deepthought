package io.biza.deepthought.data.persistence.model.customer.bank;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
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
@Table(name = "CUSTOMER_BANK_ACCOUNT")
public class CustomerBankAccountData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID", nullable = false)
  @ToString.Exclude
  private CustomerData customer;

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @ToString.Exclude
  private BankAccountData account;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CARD_ID")
  @ToString.Exclude
  private CustomerBankAccountCardData card;
  
  @Column(name = "OWNER")
  @Type(type = "true_false")
  Boolean owner;
  
}
