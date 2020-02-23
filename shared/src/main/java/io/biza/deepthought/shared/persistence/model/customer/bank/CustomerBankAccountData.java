package io.biza.deepthought.shared.persistence.model.customer.bank;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.shared.persistence.model.customer.CustomerData;
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
@Table(name = "CUSTOMER_BANK_ACCOUNT",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"CUSTOMER_ID", "ACCOUNT_ID"})})
public class CustomerBankAccountData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID", nullable = false, foreignKey = @ForeignKey(name = "CUSTOMER_BANK_ACCOUNT_CUSTOMER_ID_FK"))
  @ToString.Exclude
  CustomerData customer;

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false, foreignKey = @ForeignKey(name = "CUSTOMER_BANK_ACCOUNT_ACCOUNT_ID_FK"))
  @ToString.Exclude
  BankAccountData account;

  @Column(name = "OWNER")
  @Type(type = "true_false")
  Boolean owner;
  
  @PrePersist
  public void prePersist() {
    if(this.customer() != null) {
      Set<CustomerBankAccountData> set = new HashSet<CustomerBankAccountData>();
      if (this.customer().accounts() != null) {
        set.addAll(this.customer().accounts());
      }
      set.add(this);
      this.customer().accounts(set);
    }
    
    if(this.account() != null) {
      Set<CustomerBankAccountData> set = new HashSet<CustomerBankAccountData>();
      if (this.account().customerAccounts() != null) {
        set.addAll(this.account().customerAccounts());
      }
      set.add(this);
      this.account().customerAccounts(set);
    }
  }

}
