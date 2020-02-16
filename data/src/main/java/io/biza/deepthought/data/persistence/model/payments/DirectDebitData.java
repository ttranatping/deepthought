package io.biza.deepthought.data.persistence.model.payments;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.account.AccountCreditCardData;
import io.biza.deepthought.data.persistence.model.account.AccountData;
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
@Table(name = "DIRECT_DEBIT")
public class DirectDebitData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  DioSchemeType schemeType = DioSchemeType.DIO_BANKING;

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @ToString.Exclude
  @NotNull
  AccountData account;
  
  @ManyToOne
  @JoinColumn(name = "AUTHORISED_ENTITY_ID", nullable = false)
  @ToString.Exclude
  AuthorisedEntityData authorisedEntity;
  
  @Column(name = "LAST_DEBIT_DATETIME")
  OffsetDateTime lastDebitDateTime;
  
  @Column(name = "LAST_DEBIT_AMOUNT")
  BigDecimal lastDebitAmount;
  
  @PrePersist
  public void prePersist() {
    if (this.authorisedEntity() != null) {
      Set<DirectDebitData> set = new HashSet<DirectDebitData>();
      if (this.authorisedEntity().directDebits() != null) {
        set.addAll(this.authorisedEntity().directDebits());
      }
      set.add(this);
      this.authorisedEntity().directDebits(set);
    }
  }
  
}
