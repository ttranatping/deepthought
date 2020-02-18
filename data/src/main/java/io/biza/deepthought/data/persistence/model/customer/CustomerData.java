package io.biza.deepthought.data.persistence.model.customer;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankPayeeData;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.customer.bank.CustomerBankAccountData;
import io.biza.deepthought.data.persistence.model.organisation.OrganisationData;
import io.biza.deepthought.data.persistence.model.person.PersonData;
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
@Table(name = "CUSTOMER")
public class CustomerData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_COMMON;
  
  @Column(name = "CREATION_TIME", updatable = false, insertable = false)
  @CreationTimestamp
  @Builder.Default
  OffsetDateTime creationTime = OffsetDateTime.now();

  @Column(name = "LAST_UPDATED", updatable = false, insertable = false)
  @UpdateTimestamp
  @Builder.Default
  OffsetDateTime lastUpdated = OffsetDateTime.now();
  
  @ManyToOne
  @JoinColumn(name = "BRAND_ID", nullable = false)
  @ToString.Exclude
  BrandData brand;
  
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<CustomerBankAccountData> accounts;
  
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<CustomerBankScheduledPaymentData> scheduledPayments;
  
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<CustomerBankPayeeData> payees;
    
  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
  PersonData person;
  
  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
  OrganisationData organisation;
  
  @AssertTrue(
      message = "Only one of PersonData or OrganisationData can be populated")
  private boolean isPersonXorOrganisation() {
    return (person != null && organisation == null) || (person == null && organisation != null);
  }
  
  @PrePersist
  public void prePersist() {
    if (this.person != null) {
      this.person.customer(this);
    }
    
    if(this.organisation != null) {
      this.organisation.customer(this);
    }
  }
  
}
