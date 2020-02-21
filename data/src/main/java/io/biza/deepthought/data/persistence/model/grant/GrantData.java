package io.biza.deepthought.data.persistence.model.grant;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.customer.bank.CustomerBankAccountData;
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
@Table(name = "GRANT", indexes = {@Index(columnList = "subject", name = "subject_index")})
public class GrantData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @Column(name = "SUBJECT")
  @NotNull
  private String subject;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @ToString.Exclude
  @NotNull
  @Size(max = 1) // TODO: Support just 1:1 subject to customer for now but setup Hibernate to do 1:M for the future
  Set<GrantCustomerData> customers;

  @OneToMany(mappedBy = "grant", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<GrantAccountData> accounts;
  
  @Column(name = "CREATED", insertable = false, updatable = false)
  @CreationTimestamp
  @Builder.Default
  @NotNull
  OffsetDateTime created = OffsetDateTime.now();
  
  @Column(name = "EXPIRY")
  @NotNull
  @Builder.Default
  OffsetDateTime expiry = OffsetDateTime.now().plusDays(30);

}
