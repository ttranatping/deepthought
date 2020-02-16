package io.biza.deepthought.data.persistence.model.payments;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioSchemeType;
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
@Table(name = "PAYEE")
public class PayeeData {

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

  @OneToMany(mappedBy = "payee", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<ScheduledPaymentSetData> paymentSet;
  
  @Column(name = "NICK_NAME", nullable = false)
  @NotNull
  String nickName;
  
  @Column(name = "DESCRIPTION")
  String description;
  
  @Column(name = "CREATION_DATE_TIME")
  @CreationTimestamp
  OffsetDateTime creationDateTime;
  
  @OneToOne(mappedBy = "payee", cascade = CascadeType.ALL, optional = true)
  PayeeDomesticData domestic;
  
  @OneToOne(mappedBy = "payee", cascade = CascadeType.ALL, optional = true)
  PayeeBPAYData bpay;
  
  @OneToOne(mappedBy = "payee", cascade = CascadeType.ALL, optional = true)
  PayeeInternationalData international;
}
