package io.biza.deepthought.shared.persistence.model.grant;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioGrantAccess;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountData;
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
@Table(name = "GRANT_ACCOUNT",
uniqueConstraints = {@UniqueConstraint(columnNames = {"GRANT_ID", "ACCOUNT_ID"})})
public class GrantAccountData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @ManyToOne
  @JoinColumn(name = "GRANT_ID", nullable = false, foreignKey = @ForeignKey(name = "GRANT_ACCOUNT_GRANT_ID_FK"))
  @NotNull
  @ToString.Exclude
  GrantData grant;
  
  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false, foreignKey = @ForeignKey(name = "GRANT_ACCOUNT_ACCOUNT_ID_FK"))
  @NotNull
  @ToString.Exclude
  BankAccountData account;
  
  @Column(name = "ACCESS")
  @Enumerated(EnumType.STRING)
  DioGrantAccess access;
  
  @PrePersist
  public void prePersist() {
    if (this.grant() != null) {
      Set<GrantAccountData> set = new HashSet<GrantAccountData>();
      if (this.grant().accounts() != null) {
        set.addAll(this.grant().accounts());
      }
      set.add(this);
      this.grant().accounts(set);
    }
    
    if (this.account() != null) {
      Set<GrantAccountData> set = new HashSet<GrantAccountData>();
      if (this.account().grants() != null) {
        set.addAll(this.account().grants());
      }
      set.add(this);
      this.account().grants(set);
    }
  }
}
